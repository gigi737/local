<template>
    <div>
        <el-row :gutter="20">
            <el-col :span="24">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span style="color:red">出库统计</span>

                    </div>
                    <div id="echartDiv" style="height: 500px;"></div>
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

        };
    },
    created() {
        this.GetStockOutByDayEchartApi();
    },
    mounted() {

    },
    methods: {
        //出库数据
        async GetStockOutByDayEchartApi() {
            let { Data } = await this.$Post(`/StockOut/GetStockOutByDayEchart`, { WarehouseId: this.CurrentWarehouseId });
            Data = Data.reverse();


            this.$nextTick(() => {
                let option = {
                    title: {
                        text: '最近一年出库统计'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'cross',
                            label: {
                                backgroundColor: '#6a7985'
                            }
                        }
                    },
                    legend: {
                        data: ['待出库', '完成分配', '出库完成']
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
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: false,
                            data: Data.map(item => item.date),

                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'
                        }
                    ],
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
                    series: [
                        {
                            name: '待出库',
                            type: 'line',
                            stack: 'Total',
                            areaStyle: {},
                            emphasis: {
                                focus: 'series'
                            },
                            data: Data.map(item => item.waitOutStatusCount)
                        },
                        {
                            name: '完成分配',
                            type: 'line',
                            stack: 'Total',
                            areaStyle: {},
                            emphasis: {
                                focus: 'series'
                            },
                            data: Data.map(item => item.completeAssignStatusCount)
                        },
                        {
                            name: '出库完成',
                            type: 'line',
                            stack: 'Total',
                            areaStyle: {},
                            emphasis: {
                                focus: 'series'
                            },
                            data: Data.map(item => item.outCompleteStatusCount)
                        },


                    ]
                };

                this.myChart = echarts.init(document.getElementById("echartDiv"));
                this.myChart.setOption(option);

                window.addEventListener("resize", () => {
                    this.myChart.resize();
                });
            });
        },
    }
}
</script>
