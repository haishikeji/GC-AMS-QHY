<template>
  <el-dialog
    :title="!dataFormHead.orderNo ? '新增' : '修改'"
    :close-on-click-modal="false"
    :fullscreen="true"
    :modal="false"
    :visible.sync="visible">
    <div style="border: 1px solid rgba(0, 0, 0, 0.1);height: 150px;margin-top: -30px;padding: 10px;">
      <el-form :model="dataFormHead" ref="dataFormHead" label-width="105px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="出库单号" prop="orderNo">
              <el-input v-model="dataFormHead.orderNo" placeholder="" :disabled="true"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="单据类型" prop="orderType">
              <el-select v-model="dataFormHead.orderType" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.so_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="单据状态" prop="orderStatus">
              <el-select v-model="dataFormHead.orderStatus" :disabled=" id === '' ? true : false" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.so_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发货时间" prop="shipmentTime">
              <el-date-picker
                v-model="dataFormHead.shipmentTime"
                type="date"
                placeholder="选择日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd" style="width: 100%">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户" prop="customerId">
              <el-select v-model="dataFormHead.customerId" placeholder="请选择" style="width: 100%" clearable>
                <el-option
                  v-for="item in customerIdCombo"
                  :key="item.value"
                  :label="item.customerName"
                  :value="item.customerId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户单号1" prop="soReference1">
              <el-input v-model="dataFormHead.soReference1" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户单号2" prop="soReference2">
              <el-input v-model="dataFormHead.soReference2" placeholder=""></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="text-align: right;">
            <el-button size="mini" type="primary" :disabled="viewTag || isAdd === false" @click="dataFormHeadSubmit()">提交</el-button>
            <el-button size="mini" type="primary" :disabled="viewTag || isAdd === true" @click="dataFormHeadSubmit()">修改</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <div>
      <el-tabs v-model="activeTap">
        <el-tab-pane label="出库明细" name="orderDetails">
          <OrderDetails ref="orderDetails"/>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-dialog>
</template>

<script>
  import { initOrderHeader, addDocOrderHeader, updateDocOrderHeader } from '@/api/docOrder/docOrder'
  import OrderDetails from './docOrderDetails'
  import {queryCustomerDict} from "@/api/base/customer";

  export default {
    dicts: ['so_status', 'so_type'],
    data () {
      return {
        choseId: '',
        id: '',
        visible: false,
        roleList: [],
        dataFormHead: {
          orderNo: '',
          orderType: 'OD',
          orderStatus: '',
          shipmentTime: '',
          soReference1: '',
          soReference2: '',
          soReference3: '',
          customerId: 1
        },
        activeTap: 'orderDetails',
        customerIdCombo: [],
        addDisable: false,
        viewTag: false,
        isAdd: true
      }
    },
    components: {
      OrderDetails
    },
    methods: {
      initData () {
        queryCustomerDict().then(response => {
          this.customerIdCombo = response.data;
        });
      },
      init (id, viewTag) {
        this.viewTag = viewTag
        this.initData()
        this.visible = true
        this.$nextTick(() => {
          initOrderHeader(id).then(response => {
            if(response.code == 200) {
              this.isAdd = response.data.isAdd
              let orderHeader = response.data.header;
              this.dataFormHead.orderNo = orderHeader.orderNo
              this.dataFormHead.orderType = orderHeader.orderType
              this.dataFormHead.orderStatus = orderHeader.orderStatus
              this.dataFormHead.shipmentTime = orderHeader.shipmentTime
              this.dataFormHead.soReference1 = orderHeader.soReference1
              this.dataFormHead.soReference2 = orderHeader.soReference2
              this.dataFormHead.soReference3 = orderHeader.soReference3
              if(orderHeader.customerId && orderHeader.customerId != '') {
                this.dataFormHead.customerId = parseInt(orderHeader.customerId)
              }
              this.$refs.orderDetails.getDataList(this.dataFormHead.orderNo,this.viewTag)
            } else {
              this.$modal.msgError(response.msg);
            }
          })
        })
      },
      // 表单提交
      dataFormHeadSubmit () {
        if(this.isAdd) {
          addDocOrderHeader(this.dataFormHead).then(response => {
            if(response.code == 200) {
              this.$modal.msgSuccess(response.msg);
              this.addDisable = true
            } else {
              this.$modal.msgError(response.msg);
            }
            this.$emit('refreshDataList')
          })
        } else {
          updateDocOrderHeader(this.dataFormHead).then(response => {
            if(response.code == 200) {
              this.$modal.msgSuccess(response.msg);
            } else {
              this.$modal.msgError(response.msg);
            }
            this.$emit('refreshDataList')
          })
        }
      }
    }
  }
</script>
