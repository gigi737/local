<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="24">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="color:red">数据统计</span>
                    </div>
                    <el-row :gutter="20">
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-1">
                                <div class="data-header">库存总量</div>
                                <div class="data-value">{{ DateCollect.TotalQty }}</div>
                                <div class="data-unit">件</div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-2">
                                <div class="data-header">库位使用率</div>
                                <div class="data-value">{{ DateCollect.UseBinRate }}%</div>
                                <div class="data-unit">已使用 {{ DateCollect.UseBinCount }}/{{ DateCollect.BinCount }}
                                </div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-3">
                                <div class="data-header">商品总数</div>
                                <div class="data-value">{{ DateCollect.CargoCount }}</div>
                                <div class="data-unit">种</div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-4">
                                <div class="data-header">客户数量</div>
                                <div class="data-value">{{ DateCollect.CustomerCount }}</div>
                                <div class="data-unit">个</div>
                            </el-card>
                        </el-col>
                    </el-row>
                    <el-row :gutter="20" style="margin-top: 20px;">
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-5">
                                <div class="data-header">入库完成数量</div>
                                <div class="data-value">{{ DateCollect.StockInCompletedCount }}</div>
                                <div class="data-unit">单</div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-6">
                                <div class="data-header">出库完成数量</div>
                                <div class="data-value">{{ DateCollect.StockOutCompletedCount }}</div>
                                <div class="data-unit">单</div>
                            </el-card>
                        </el-col>
                        <el-col :span="6">
                            <el-card shadow="hover" class="data-card card-7">
                                <div class="data-header">完成盘点数量</div>
                                <div class="data-value">{{ DateCollect.InventoryCheckCompletedCount }}</div>
                                <div class="data-unit">单</div>
                            </el-card>
                        </el-col>
                    </el-row>

                </el-card>
            </el-col>
            <el-col :span="8" style="margin-top: 20px;">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="color:red">显示缺货数量最大的50个商品的补货信息</span>

                    </div>
                    <div id="echartDiv" style="height: 500px;"></div>
                </el-card>

            </el-col>
            <el-col :span="8" style="margin-top: 20px;">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="color:red">入库单紧急程度</span>

                    </div>
                    <div style="height: 500px;">
                        <el-table :data="StockInOrderUrgencyDtoList" style="width: 100%"
                            :row-class-name="tableRowClassName">
                            <el-table-column label="单号" width="180" :align="'center'">
                                <template slot-scope="scope">

                                    <span style="margin-left: 10px">{{ scope.row.No }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="剩余时间" :align="'center'">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px"
                                        :style="{ color: scope.row.RemindSecond < 0 ? 'red' : 'inherit' }">
                                        {{ formatRemindTime(scope.row.RemindSecond) }}
                                    </span>
                                </template>
                            </el-table-column>
                            <el-table-column label="紧急程度" :align="'center'">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">
                                        {{ scope.row.EmergencyRankFormat }}
                                    </span>
                                </template>
                            </el-table-column>
                        </el-table>

                    </div>
                </el-card>

            </el-col>
            <el-col :span="8" style="margin-top: 20px;">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="color:red">出库单紧急程度</span>

                    </div>
                    <div style="height: 500px;">
                        <el-table :data="StockOutOrderUrgencyDtoList" style="width: 100%"
                            :row-class-name="tableRowClassName">
                            <el-table-column label="单号" width="180" :align="'center'">
                                <template slot-scope="scope">

                                    <span style="margin-left: 10px">{{ scope.row.No }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="剩余时间" :align="'center'">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px"
                                        :style="{ color: scope.row.RemindSecond < 0 ? 'red' : 'inherit' }">
                                        {{ formatRemindTime(scope.row.RemindSecond) }}
                                    </span>
                                </template>
                            </el-table-column>
                            <el-table-column label="紧急程度" :align="'center'">
                                <template slot-scope="scope">
                                    <span style="margin-left: 10px">
                                        {{ scope.row.EmergencyRankFormat }}
                                    </span>
                                </template>
                            </el-table-column>
                        </el-table>

                    </div>
                </el-card>

            </el-col>
        </el-row>
    </div>
</template>

<script>
import PaginationBox from '@/components/Pagination/PaginationBox.vue';
import * as echarts from "echarts";
import { mapGetters } from 'vuex';
export default {
    name: 'Home',
    props: {

    },
    components: {
        PaginationBox
    },
    computed: {
        ...mapGetters([
            'Token', 'UserInfo', 'RoleType', 'HasUserInfo', 'ColumnType', "UserId", 'CurrentWarehouseId'
        ])
    },
    data() {
        return {
            myChart: null,
            CargoReplenishmentDtoList: [],
            DateCollect: {},
            StockInOrderUrgencyDtoList: [],
            StockOutOrderUrgencyDtoList: []
        };
    },
    created() {
        this.GetCargoReplenishmentDtoListApi();
        this.GetDateCollectApi();
        this.GetStockInOrderUrgencyApi();
        this.GetStockOutOrderUrgencyApi();
    },
    mounted() {

    },
    methods: {
        formatRemindTime(seconds) {
            if (seconds < 0) {
                return `超时 ${this.formatDuration(Math.abs(seconds))}`;
            }
            return this.formatDuration(seconds);
        },

        formatDuration(seconds) {
            const days = Math.floor(seconds / (24 * 60 * 60));
            const hours = Math.floor((seconds % (24 * 60 * 60)) / (60 * 60));
            const minutes = Math.floor((seconds % (60 * 60)) / 60);

            let result = '';
            if (days > 0) result += `${days}天`;
            if (hours > 0) result += `${hours}小时`;
            if (minutes > 0) result += `${minutes}分钟`;

            return result || '不到1分钟';
        },
        //补货数据
        async GetCargoReplenishmentDtoListApi() {
            let { Data: { Items } } = await this.$Post(`/InventoryRecord/GetCargoReplenishmentDtoList`, { WarehouseId: this.CurrentWarehouseId, Limit: 50 });
            this.CargoReplenishmentDtoList = Items;

            this.$nextTick(() => {
                let option = {
                    title: {
                        text: '冷库补货排行',
                        subtext: '按缺货数量排序',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        },
                        formatter: function (params) {
                            const data = params[0];
                            return `${data.name}<br/>
                                    缺货数量: ${data.value}<br/>
                                    补货建议: ${data.value > 100 ? '建议立即补货' : '可考虑补货'}`
                        }
                    },
                    toolbox: {
                        show: true,
                        feature: {
                            dataView: { show: true, readOnly: false },
                            magicType: { show: true, type: ['line', 'bar'] },
                            restore: { show: true },
                            saveAsImage: { show: true }
                        }
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '5%',
                        containLabel: true
                    },
                    xAxis: [{
                        type: 'category',
                        data: Items.map(x => x.CargoDto.Name).reverse(),
                        axisLabel: {
                            interval: 0,
                            rotate: 0
                        }
                    }],
                    yAxis: [{
                        type: 'value',
                        name: '缺货数量',
                        axisLabel: {
                            formatter: '{value}'
                        }
                    }],
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    dataZoom: [{
                        type: 'slider',
                        show: true,
                        start: 0,
                        end: 100
                    }, {
                        type: 'inside',
                        start: 0,
                        end: 100
                    }],
                    series: [{
                        name: '缺货数量',
                        type: 'bar',
                        barWidth: '40%',
                        itemStyle: {
                            color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                                { offset: 0, color: '#83bff6' },
                                { offset: 0.5, color: '#188df0' },
                                { offset: 1, color: '#188df0' }
                            ]),
                            borderRadius: [5, 5, 0, 0]
                        },
                        data: Items.map(x => x.NeedQty).reverse(),
                        label: {
                            show: true,
                            position: 'top'
                        }
                    }]
                };

                this.myChart = echarts.init(document.getElementById("echartDiv"));
                this.myChart.setOption(option);

                window.addEventListener("resize", () => {
                    this.myChart.resize();
                });
            });
        },
        //数据统计
        async GetDateCollectApi() {
            let { Data } = await this.$Post(`/InventoryRecord/GetDateCollect`, { WarehouseId: this.CurrentWarehouseId });
            this.DateCollect = Data;
        },
        //得到入库单的紧急程度接口
        async GetStockInOrderUrgencyApi() {
            let { Data: { Items } } = await this.$Post(`/StockIn/List`, { WarehouseId: this.CurrentWarehouseId, IsNotComplted: true });
            this.StockInOrderUrgencyDtoList = Items;
        },
        //得到出库单的紧急程度接口
        async GetStockOutOrderUrgencyApi() {
            let { Data: { Items } } = await this.$Post(`/StockOut/List`, { WarehouseId: this.CurrentWarehouseId, IsNotComplted: true });
            this.StockOutOrderUrgencyDtoList = Items;
        },
        tableRowClassName({ row, rowIndex }) {
            console.log(row.EmergencyRank);
            switch (row.EmergencyRank) {
                case 1:
                    return 'urgency-low';
                case 2:
                    return 'urgency-medium';
                case 3:
                    return 'urgency-high';
                case 4:
                    return 'urgency-critical';
                default:
                    return '';
            }

        }
    }
}       
</script>


<style scoped>
.data-card {
    text-align: center;
    padding: 20px;
    transition: all 0.3s;
}

.data-card:hover {
    transform: translateY(-5px);
}

.data-header {
    font-size: 16px;
    margin-bottom: 10px;
    color: #ffffff;
}

.data-value {
    font-size: 28px;
    font-weight: bold;
    margin-bottom: 5px;
    color: #ffffff;
}

.data-unit {
    font-size: 14px;
    color: rgba(255, 255, 255, 0.8);
}

/* 不同卡片的渐变背景色 */
.card-1 {
    background: linear-gradient(135deg, #1bbc9b 0%, #1bbc9b 100%);
}

.card-2 {
    background: linear-gradient(135deg, #3498db 0%, #2980b9 100%);
}

.card-3 {
    background: linear-gradient(135deg, #9b59b6 0%, #8e44ad 100%);
}

.card-4 {
    background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
}

.card-5 {
    background: linear-gradient(135deg, #f1c40f 0%, #f39c12 100%);
}

.card-6 {
    background: linear-gradient(135deg, #2ecc71 0%, #27ae60 100%);
}

.card-7 {
    background: linear-gradient(135deg, #e67e22 0%, #d35400 100%);
}

.card-8 {
    background: linear-gradient(135deg, #16a085 0%, #1abc9c 100%);
}

.card-9 {
    background: linear-gradient(135deg, #34495e 0%, #2c3e50 100%);
}

/* 添加卡片阴影效果 */
.el-card {
    border: none;
    border-radius: 8px;
}

.el-card.is-hover-shadow:hover {
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}
</style>