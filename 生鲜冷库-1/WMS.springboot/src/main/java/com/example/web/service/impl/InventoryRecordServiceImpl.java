package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.enums.InventoryCheckStatusEnum;
import com.example.web.enums.StockInStatusEnum;
import com.example.web.enums.StockOutStatusEnum;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import lombok.SneakyThrows;

import com.example.web.tools.*;

/**
 * 库存记录功能实现类
 */
@Service
public class InventoryRecordServiceImpl extends ServiceImpl<InventoryRecordMapper, InventoryRecord> implements InventoryRecordService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的InventoryRecord表mapper对象
     */
    @Autowired
    private InventoryRecordMapper InventoryRecordMapper;
    @Autowired
    private WarehouseBinMapper WarehouseBinMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;
    @Autowired
    private CustomerMapper CustomerMapper;
    @Autowired
    private CargoMapper CargoMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private StockInMapper stockInMapper;

    @Autowired
    private StockOutMapper stockOutMapper;

    @Autowired
    private InventoryCheckMapper InventoryCheckMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<InventoryRecord> BuilderQuery(InventoryRecordPagedInput input) {
        //声明一个支持库存记录查询的(拉姆达)表达式
        LambdaQueryWrapper<InventoryRecord> queryWrapper = Wrappers.<InventoryRecord>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, InventoryRecord::getId, input.getId())
                .eq(input.getCreatorId() != null, InventoryRecord::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRecord::getWarehouseId, input.getWarehouseId());
        }

        if (input.getCustomerId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRecord::getCustomerId, input.getCustomerId());
        }

        if (input.getCargoId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRecord::getCargoId, input.getCargoId());
        }

        if (input.getWarehouseBinId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRecord::getWarehouseBinId, input.getWarehouseBinId());
        }
        return queryWrapper;
    }

    /**
     * 处理库存记录对于的外键数据
     */
    private InventoryRecordDto DispatchItem(InventoryRecordDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的WarehouseBin表信息
        WarehouseBin WarehouseBinEntity = WarehouseBinMapper.selectById(item.getWarehouseBinId());
        item.setWarehouseBinDto(WarehouseBinEntity.MapToDto());


        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());


        //查询出关联的Customer表信息
        Customer CustomerEntity = CustomerMapper.selectById(item.getCustomerId());
        item.setCustomerDto(CustomerEntity.MapToDto());


        //查询出关联的Cargo表信息
        Cargo CargoEntity = CargoMapper.selectById(item.getCargoId());
        item.setCargoDto(CargoEntity.MapToDto());

        return item;
    }

    /**
     * 库存记录分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<InventoryRecordDto> List(InventoryRecordPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<InventoryRecord> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(InventoryRecord::getCreationTime);
        //构建一个分页查询的model
        Page<InventoryRecord> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取库存记录数据
        IPage<InventoryRecord> pageRecords = InventoryRecordMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = InventoryRecordMapper.selectCount(queryWrapper);
        //把InventoryRecord实体转换成InventoryRecord传输模型
        List<InventoryRecordDto> items = Extension.copyBeanList(pageRecords.getRecords(), InventoryRecordDto.class);

        for (InventoryRecordDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 缺货列表
     */
    @SneakyThrows
    @Override
    public PagedResult<CargoReplenishmentDto> GetCargoReplenishmentDtoList(InventoryRecordPagedInput input) {
        ArrayList<CargoReplenishmentDto> cargoReplenishmentDtos = new ArrayList<>();
        //构建where条件+排序
        LambdaQueryWrapper<InventoryRecord> queryWrapper = BuilderQuery(input);
        List<InventoryRecord> inventoryRecords = InventoryRecordMapper.selectList(queryWrapper);

        List<Cargo> cargos = CargoMapper.selectList(Wrappers.<Cargo>lambdaQuery().eq(Cargo::getWarehouseId, input.getWarehouseId()));

        for (Cargo cargo : cargos) {

            CargoReplenishmentDto cargoReplenishmentDto = new CargoReplenishmentDto();
            cargoReplenishmentDto.setCargoId(cargo.getId());
            List<InventoryRecord> list = inventoryRecords.stream().filter(x -> cargo.getId().equals(x.getCargoId())).toList();
            if (list.size() > 0) {
                cargoReplenishmentDto.setCurrentQty(list.stream().mapToDouble(x -> x.getStockQty()).sum());
            } else {
                cargoReplenishmentDto.setCurrentQty(0.0);
            }

            cargoReplenishmentDto.setCustomerId(cargo.getCustomerId());


            //查询出关联的Customer表信息
            Customer CustomerEntity = CustomerMapper.selectById(cargoReplenishmentDto.getCustomerId());
            cargoReplenishmentDto.setCustomerDto(CustomerEntity.MapToDto());


            //查询出关联的Cargo表信息
            Cargo CargoEntity = CargoMapper.selectById(cargoReplenishmentDto.getCargoId());
            cargoReplenishmentDto.setCargoDto(CargoEntity.MapToDto());


            cargoReplenishmentDto.setNeedQty(cargoReplenishmentDto.getCargoDto().getMinStockAlert() - cargoReplenishmentDto.getCurrentQty());
            if (cargoReplenishmentDto.getNeedQty() > 0) {
                cargoReplenishmentDtos.add(cargoReplenishmentDto);
            }
        }


        //cargoReplenishmentDtos根据数量从小到大排序
        cargoReplenishmentDtos.sort(Comparator.comparing(CargoReplenishmentDto::getNeedQty));
        //返回一个分页结构给前端
        return Extension.PagedResultBuild(cargoReplenishmentDtos, input);

    }

    /**
     * 单个库存记录查询
     */
    @SneakyThrows
    @Override
    public InventoryRecordDto Get(InventoryRecordPagedInput input) {
        if (input.getId() == null) {
            return new InventoryRecordDto();
        }
        return List(input).getItems().stream().findFirst().orElse(new InventoryRecordDto());
    }

    /**
     * 库存记录创建或者修改
     */
    @SneakyThrows
    @Override
    public InventoryRecordDto CreateOrEdit(InventoryRecordDto input) {
        //声明一个库存记录实体
        InventoryRecord InventoryRecord = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(InventoryRecord);
        //把传输模型返回给前端
        return InventoryRecord.MapToDto();
    }

    /**
     * 库存记录删除
     */
    @Override
    public void Delete(IdInput input) {
        InventoryRecord entity = InventoryRecordMapper.selectById(input.getId());
        InventoryRecordMapper.deleteById(entity);
    }

    /**
     * 库存记录批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

    /**
     * 数据统计
     *
     */
    @Override
    public HashMap<String, Object> GetDateCollect(InventoryRecordPagedInput input) {

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        //统计当前仓库的总库位数量
        List<WarehouseBin> warehouseBins = WarehouseBinMapper.selectList(Wrappers.<WarehouseBin>lambdaQuery().eq(WarehouseBin::getWarehouseId, input.getWarehouseId()));
        stringObjectHashMap.put("BinCount", warehouseBins.size());
        //得到已使用的库位数
        long useBinCount = InventoryRecordMapper.selectList(Wrappers.<InventoryRecord>lambdaQuery().eq(InventoryRecord::getWarehouseId, input.getWarehouseId()).gt(InventoryRecord::getStockQty, 0)).stream().map(x -> x.getWarehouseBinId()).distinct().count();
        stringObjectHashMap.put("UseBinCount", useBinCount);

        //库位使用率
        if(warehouseBins.size()>0){
            stringObjectHashMap.put("UseBinRate", String.format("%.2f", (double)useBinCount/warehouseBins.size()*100));
        }
        else{
            stringObjectHashMap.put("UseBinRate", 0);
        }


        //总货主数量
        Long CustomerCount = CustomerMapper.selectCount(Wrappers.<Customer>lambdaQuery().eq(Customer::getWarehouseId, input.getWarehouseId()));
        stringObjectHashMap.put("CustomerCount", CustomerCount);

        //总商品数量
        Long CargoCount = CargoMapper.selectCount(Wrappers.<Cargo>lambdaQuery().eq(Cargo::getWarehouseId, input.getWarehouseId()));
        stringObjectHashMap.put("CargoCount", CargoCount);

        //系统总库存数量
        List<InventoryRecord> inventoryRecords = InventoryRecordMapper.selectList(Wrappers.<InventoryRecord>lambdaQuery().eq(InventoryRecord::getWarehouseId, input.getWarehouseId()));
        if (inventoryRecords.size() > 0) {
            double totalQty = inventoryRecords.stream().mapToDouble(x -> x.getStockQty()).sum();
            stringObjectHashMap.put("TotalQty", totalQty);
        } else {
            stringObjectHashMap.put("TotalQty", 0);
        }



        //完成入库单量
        Long StockInCompletedCount = stockInMapper.selectCount(Wrappers.<StockIn>lambdaQuery().eq(StockIn::getWarehouseId, input.getWarehouseId()).eq(StockIn::getStockInStatus, StockInStatusEnum.入库完成.index()));
        stringObjectHashMap.put("StockInCompletedCount",StockInCompletedCount);
        //完成出库单量
        Long StockOutCompletedCount = stockOutMapper.selectCount(Wrappers.<StockOut>lambdaQuery().eq(StockOut::getWarehouseId, input.getWarehouseId()).eq(StockOut::getStockOutStatus, StockOutStatusEnum.出库完成.index()));
        stringObjectHashMap.put("StockOutCompletedCount",StockOutCompletedCount);

        //完成盘点单量
        Long InventoryCheckCompletedCount = InventoryCheckMapper.selectCount(Wrappers.<InventoryCheck>lambdaQuery().eq(InventoryCheck::getWarehouseId, input.getWarehouseId()).eq(InventoryCheck::getInventoryCheckStatus, InventoryCheckStatusEnum.盘点完成.index()));
        stringObjectHashMap.put("InventoryCheckCompletedCount",InventoryCheckCompletedCount);




        return stringObjectHashMap;
    }
}
