package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

import com.example.web.tools.*;

/**
 * 库存流水记录功能实现类
 */
@Service
public class InventoryRuningRecordServiceImpl extends ServiceImpl<InventoryRuningRecordMapper, InventoryRuningRecord> implements InventoryRuningRecordService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的InventoryRuningRecord表mapper对象
     */
    @Autowired
    private InventoryRuningRecordMapper InventoryRuningRecordMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;
    @Autowired
    private CargoMapper CargoMapper;
    @Autowired
    private CustomerMapper CustomerMapper;

    @Autowired
    private StockInMapper StockInMapper;

    @Autowired
    private StockInDetMapper StockInDetMapper;

    @Autowired
    private WarehouseBinMapper WarehouseBinMapper;


    @Autowired
    private InventoryRecordMapper InventoryRecordMapper;

    @Autowired
    private StockOutMapper StockOutMapper;

    @Autowired
    private StockOutDetMapper StockOutDetMapper;

    @Autowired
    private InventoryCheckMapper InventoryCheckMapper;

    @Autowired
    private InventoryCheckDetMapper InventoryCheckDetMapper;

    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<InventoryRuningRecord> BuilderQuery(InventoryRuningRecordPagedInput input) {
        //声明一个支持库存流水记录查询的(拉姆达)表达式
        LambdaQueryWrapper<InventoryRuningRecord> queryWrapper = Wrappers.<InventoryRuningRecord>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, InventoryRuningRecord::getId, input.getId())
                .eq(input.getCreatorId() != null, InventoryRuningRecord::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getType())) {
            queryWrapper = queryWrapper.like(InventoryRuningRecord::getType, input.getType());
        }
        if (Extension.isNotNullOrEmpty(input.getRelativeNo())) {
            queryWrapper = queryWrapper.like(InventoryRuningRecord::getRelativeNo, input.getRelativeNo());
        }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRuningRecord::getWarehouseId, input.getWarehouseId());
        }

        if (input.getCargoId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRuningRecord::getCargoId, input.getCargoId());
        }

        if (input.getCustomerId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRuningRecord::getCustomerId, input.getCustomerId());
        }
        if (input.getWarehouseBinId() != null) {
            queryWrapper = queryWrapper.eq(InventoryRuningRecord::getWarehouseBinId, input.getWarehouseBinId());
        }
        return queryWrapper;
    }

    /**
     * 处理库存流水记录对于的外键数据
     */
    private InventoryRuningRecordDto DispatchItem(InventoryRuningRecordDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());


        //查询出关联的Cargo表信息
        Cargo CargoEntity = CargoMapper.selectById(item.getCargoId());
        item.setCargoDto(CargoEntity.MapToDto());


        //查询出对应的库位
        WarehouseBin WarehouseBinEntity = WarehouseBinMapper.selectById(item.getWarehouseBinId());
        item.setWarehouseBinDto(WarehouseBinEntity.MapToDto());

        //查询出关联的Customer表信息
        Customer CustomerEntity = CustomerMapper.selectById(item.getCustomerId());
        item.setCustomerDto(CustomerEntity.MapToDto());

        return item;
    }

    /**
     * 库存流水记录分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<InventoryRuningRecordDto> List(InventoryRuningRecordPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<InventoryRuningRecord> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(InventoryRuningRecord::getCreationTime);
        //构建一个分页查询的model
        Page<InventoryRuningRecord> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取库存流水记录数据
        IPage<InventoryRuningRecord> pageRecords = InventoryRuningRecordMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = InventoryRuningRecordMapper.selectCount(queryWrapper);
        //把InventoryRuningRecord实体转换成InventoryRuningRecord传输模型
        List<InventoryRuningRecordDto> items = Extension.copyBeanList(pageRecords.getRecords(), InventoryRuningRecordDto.class);

        for (InventoryRuningRecordDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个库存流水记录查询
     */
    @SneakyThrows
    @Override
    public InventoryRuningRecordDto Get(InventoryRuningRecordPagedInput input) {
        if (input.getId() == null) {
            return new InventoryRuningRecordDto();
        }
        InventoryRuningRecord entity = InventoryRuningRecordMapper.selectById(input.getId());
        InventoryRuningRecordDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 库存流水记录创建或者修改
     */
    @SneakyThrows
    @Override
    public InventoryRuningRecordDto CreateOrEdit(InventoryRuningRecordDto input) {
        //声明一个库存流水记录实体
        InventoryRuningRecord InventoryRuningRecord = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(InventoryRuningRecord);
        //把传输模型返回给前端
        return InventoryRuningRecord.MapToDto();
    }

    /**
     * 库存流水记录删除
     */
    @Override
    public void Delete(IdInput input) {
        InventoryRuningRecord entity = InventoryRuningRecordMapper.selectById(input.getId());
        InventoryRuningRecordMapper.deleteById(entity);
    }

    /**
     * 库存流水记录批量删除
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
     * 库存入库单完成
     */
    @Override
    public void StockInCompleted(Integer stockInId) {

        //查询入库单
        StockIn stockIn = StockInMapper.selectById(stockInId);
        //查询入库的明细
        List<StockInDet> stockInDetList = StockInDetMapper.selectList(Wrappers.<StockInDet>lambdaQuery().eq(StockInDet::getStockInId, stockInId));

        ArrayList<InventoryRuningRecord> inventoryRuningRecords = new ArrayList<>();
        for (StockInDet stockInDet : stockInDetList) {
            InventoryRuningRecord inventoryRuningRecord = new InventoryRuningRecord();
            inventoryRuningRecord.setWarehouseId(stockIn.getWarehouseId());
            inventoryRuningRecord.setType("入库完成");
            inventoryRuningRecord.setQty(stockInDet.getQty());
            inventoryRuningRecord.setRelativeNo(stockIn.getNo());
            inventoryRuningRecord.setCargoId(stockInDet.getCargoId());
            inventoryRuningRecord.setWarehouseBinId(stockInDet.getWarehouseBinId());
            inventoryRuningRecord.setCustomerId(stockIn.getCustomerId());
            inventoryRuningRecords.add(inventoryRuningRecord);
        }
        InventoryRuningRecordMapper.insert(inventoryRuningRecords);

        //把库存流水变更到库存主表
        SyncStockToInventoryRecord(stockIn.getWarehouseId(), stockIn.getNo());
    }


    /**
     * 检查是否有库存可以出库
     *
     * @param stockOutId
     */
    @Override
    public void CheckIsCanStockOut(Integer stockOutId) {

        //查询出库单
        StockOut stockOut = StockOutMapper.selectById(stockOutId);
        //查询出库的明细
        List<StockOutDet> stockOutDetList = StockOutDetMapper.selectList(Wrappers.<StockOutDet>lambdaQuery().eq(StockOutDet::getStockOutId, stockOutId));

        //查询所有的明细
        List<InventoryRuningRecord> inventoryRuningRecords = InventoryRuningRecordMapper.selectList(Wrappers.<InventoryRuningRecord>lambdaQuery()
                .eq(InventoryRuningRecord::getWarehouseId, stockOut.getWarehouseId())
                .eq(InventoryRuningRecord::getRelativeNo, stockOut.getNo()));
        for (StockOutDet stockOutDet : stockOutDetList) {
            InventoryRecord inventoryRecord = InventoryRecordMapper.selectList(Wrappers.<InventoryRecord>lambdaQuery()
                    .eq(InventoryRecord::getWarehouseBinId, stockOutDet.getWarehouseBinId())
                    .eq(InventoryRecord::getWarehouseId, stockOutDet.getWarehouseId())
                    .eq(InventoryRecord::getCargoId, stockOutDet.getCargoId())
                    .eq(InventoryRecord::getCustomerId, stockOut.getCustomerId())).stream().findFirst().orElse(null);
            if (inventoryRecord == null) {

                WarehouseBin warehouseBin = WarehouseBinMapper.selectById(stockOutDet.getWarehouseBinId());
                Cargo cargo = CargoMapper.selectById(stockOutDet.getCargoId());

                throw new CustomException("库位:" + warehouseBin.getBinCode() + "商品SKU:" + cargo.getSKU() + "库存不足");
            } else {

                if (inventoryRecord.getStockQty() < stockOutDet.getQty()) {
                    WarehouseBin warehouseBin = WarehouseBinMapper.selectById(stockOutDet.getWarehouseBinId());
                    Cargo cargo = CargoMapper.selectById(stockOutDet.getCargoId());
                    Double remindQty = inventoryRecord.getStockQty() - stockOutDet.getQty();
                    throw new CustomException("库位:" + warehouseBin.getBinCode() + "商品SKU:" + cargo.getSKU() + "库存不足,缺少数量" + remindQty);
                }
            }
        }
    }


    /**
     * 库存出库单完成
     */
    @Override
    public void StockOutCompleted(Integer stockOutId) {

        //查询出库单
        StockOut stockOut = StockOutMapper.selectById(stockOutId);
        //查询出库的明细
        List<StockOutDet> stockOutDetList = StockOutDetMapper.selectList(Wrappers.<StockOutDet>lambdaQuery().eq(StockOutDet::getStockOutId, stockOutId));

        ArrayList<InventoryRuningRecord> inventoryRuningRecords = new ArrayList<>();
        for (StockOutDet stockOutDet : stockOutDetList) {
            InventoryRuningRecord inventoryRuningRecord = new InventoryRuningRecord();
            inventoryRuningRecord.setWarehouseId(stockOutDet.getWarehouseId());
            inventoryRuningRecord.setType("出库完成");
            inventoryRuningRecord.setQty(-stockOutDet.getQty());
            inventoryRuningRecord.setRelativeNo(stockOut.getNo());
            inventoryRuningRecord.setCargoId(stockOutDet.getCargoId());
            inventoryRuningRecord.setWarehouseBinId(stockOutDet.getWarehouseBinId());
            inventoryRuningRecord.setCustomerId(stockOut.getCustomerId());
            inventoryRuningRecords.add(inventoryRuningRecord);
        }
        InventoryRuningRecordMapper.insert(inventoryRuningRecords);

        //把库存流水变更到库存主表
        SyncStockToInventoryRecord(stockOut.getWarehouseId(), stockOut.getNo());
    }


    /**
     * 盘点执行库存变动
     */
    @Override
    public void InventoryCheckCompleted(Integer inventoryCheckId) {

        //查询盘点单
        InventoryCheck inventoryCheck = InventoryCheckMapper.selectById(inventoryCheckId);
        //查询盘点的明细
        List<InventoryCheckDet> inventoryCheckDetList = InventoryCheckDetMapper.selectList(Wrappers.<InventoryCheckDet>lambdaQuery().eq(InventoryCheckDet::getInventoryCheckId, inventoryCheckId));
        ArrayList<InventoryRuningRecord> inventoryRuningRecords = new ArrayList<>();

        for (InventoryCheckDet inventoryCheckDet : inventoryCheckDetList) {
            InventoryRuningRecord inventoryRuningRecord = new InventoryRuningRecord();
            inventoryRuningRecord.setWarehouseId(inventoryCheckDet.getWarehouseId());
            inventoryRuningRecord.setType("盘点完成");

            inventoryRuningRecord.setRelativeNo(inventoryCheck.getNo());
            inventoryRuningRecord.setCargoId(inventoryCheckDet.getCargoId());
            inventoryRuningRecord.setWarehouseBinId(inventoryCheckDet.getWarehouseBinId());
            inventoryRuningRecord.setCustomerId(inventoryCheck.getCustomerId());


            InventoryRecord inventoryRecord = InventoryRecordMapper.selectList(Wrappers.<InventoryRecord>lambdaQuery()
                    .eq(InventoryRecord::getWarehouseBinId, inventoryRuningRecord.getWarehouseBinId())
                    .eq(InventoryRecord::getWarehouseId, inventoryRuningRecord.getWarehouseId())
                    .eq(InventoryRecord::getCargoId, inventoryRuningRecord.getCargoId())
                    .eq(InventoryRecord::getCustomerId, inventoryRuningRecord.getCustomerId())).stream().findFirst().orElse(null);

            //系统没有库存 直接就是盘增
            if (inventoryRecord == null) {

                inventoryRuningRecord.setQty(inventoryCheckDet.getQty());
            } else {
                //系统的库存比 实物多 调减
                if (inventoryRecord.getStockQty() > inventoryCheckDet.getQty()) {
                    double qty=inventoryRecord.getStockQty() - inventoryCheckDet.getQty();
                    inventoryRuningRecord.setQty(-qty);
                }
                else{
                     double qty=inventoryCheckDet.getQty() - inventoryRecord.getStockQty();
                     inventoryRuningRecord.setQty(qty);
                }
            }


            inventoryRuningRecords.add(inventoryRuningRecord);
        }
        InventoryRuningRecordMapper.insert(inventoryRuningRecords);

        //把库存流水变更到库存主表
        SyncStockToInventoryRecord(inventoryCheck.getWarehouseId(), inventoryCheck.getNo());

    }


    /**
     * 把库存流水变更到库存主表
     */
    public void SyncStockToInventoryRecord(Integer warehouseId, String relativeNo) {
        //查询所有的明细
        List<InventoryRuningRecord> inventoryRuningRecords = InventoryRuningRecordMapper.selectList(Wrappers.<InventoryRuningRecord>lambdaQuery()
                .eq(InventoryRuningRecord::getWarehouseId, warehouseId)
                .eq(InventoryRuningRecord::getRelativeNo, relativeNo));
        for (InventoryRuningRecord inventoryRuningRecord : inventoryRuningRecords) {

            InventoryRecord inventoryRecord = InventoryRecordMapper.selectList(Wrappers.<InventoryRecord>lambdaQuery()
                    .eq(InventoryRecord::getWarehouseBinId, inventoryRuningRecord.getWarehouseBinId())
                    .eq(InventoryRecord::getWarehouseId, inventoryRuningRecord.getWarehouseId())
                    .eq(InventoryRecord::getCargoId, inventoryRuningRecord.getCargoId())
                    .eq(InventoryRecord::getCustomerId, inventoryRuningRecord.getCustomerId())).stream().findFirst().orElse(null);
            if (inventoryRecord != null) {
                inventoryRecord.setStockQty(inventoryRecord.getStockQty() + inventoryRuningRecord.getQty());
                InventoryRecordMapper.updateById(inventoryRecord);
            } else {
                inventoryRecord = new InventoryRecord();
                inventoryRecord.setWarehouseId(inventoryRuningRecord.getWarehouseId());
                inventoryRecord.setWarehouseBinId(inventoryRuningRecord.getWarehouseBinId());
                inventoryRecord.setStockQty(inventoryRuningRecord.getQty());
                inventoryRecord.setCargoId(inventoryRuningRecord.getCargoId());
                inventoryRecord.setCustomerId(inventoryRuningRecord.getCustomerId());
                InventoryRecordMapper.insert(inventoryRecord);
            }


        }

    }


}
