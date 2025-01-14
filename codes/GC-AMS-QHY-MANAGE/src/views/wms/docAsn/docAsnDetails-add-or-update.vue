<template>
  <el-dialog
    :title="!dataForm.id ? '明细新增' : '明细修改'"
    :close-on-click-modal="false"
    :modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="入库单号" prop="asnNo">
            <el-input v-model="dataForm.asnNo" placeholder="" :disabled="true"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="物料" prop="sku">
            <el-input v-model="dataForm.sku" placeholder="">
              <i class="el-icon-search el-input__icon" slot="suffix" @click="handleSkuIconClick"></i>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="状态" prop="lineStatus">
            <el-select v-model="dataForm.lineStatus" :disabled="true" placeholder="请选择" style="width: 100%">
              <el-option
                v-for="dict in dict.type.asn_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="指定区域" prop="locationZone">
            <el-select v-model="dataForm.locationZone" @change="locationZoneChange" placeholder="请选择库区" clearable
                       size="small" style="width: 100%">
              <el-option
                v-for="dict in this.locationZoneCombo"
                :key="dict.zoneId"
                :label="dict.zoneName"
                :value="dict.zoneId"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="指定库位" prop="locationId">
            <el-select v-model="dataForm.locationId" placeholder="请选择库位" clearable size="small" style="width: 100%">
              <el-option
                v-for="dict in locationCombo"
                :key="dict.id"
                :label="dict.locationNo"
                :value="dict.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="预期数量" prop="expectedQty">
            <el-input v-model="dataForm.expectedQty" placeholder="" type="number"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <!--<el-col :span="8">
          <el-form-item label="收货数量" prop="receivedQty">
            <el-input v-model="dataForm.receivedQty" placeholder="" type="number"></el-input>
          </el-form-item>
        </el-col>-->
        <el-col :span="8">
          <el-form-item label="备注" prop="notes">
            <el-input v-model="dataForm.notes" placeholder=""></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <div style="cursor: pointer;" @click="showHidden">
        <el-divider content-position="center">批次属性信息...</el-divider>
      </div>
      <el-table v-show="attShowFlag" :data="tableOption" ref="flowConfigDetails">
        <el-table-column label="批次属性" prop="lotattId">
          <template slot-scope="scope">
            <div style="display: inline-block;width: 10px;color:red;">
              <div v-show="scope.row.lotattFlag === 'Required'">*</div>
            </div>
            <div style="display: inline-block;width: 185px;">
              {{scope.row.lotattName}}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="属性值" prop="defaultValue">
          <template slot-scope="scope">
            <div v-if="scope.row.inputType === 'Text'">
              <el-input v-model="scope.row.defaultValue" placeholder=""/>
            </div>
            <div v-if="scope.row.inputType === 'DateBox'">
              <el-date-picker v-model="scope.row.defaultValue" type="date" placeholder="" style="width: 100%"
                              value-format="yyyy-MM-dd"/>
            </div>
            <div v-if="scope.row.inputType === 'ComboBox'">
              <el-select v-model="scope.row.defaultValue" placeholder="请选择" clearable size="small" style="width: 100%">
                <el-option
                  v-for="dict in dict.type[scope.row.inputScope]"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <sku-search-dialog v-if="skuSearchDialogVisible" ref="skuSearch" @dochose="afterChoseSku"></sku-search-dialog>
    </el-form>
    <span slot="footer" class="dialog-footer">
       <el-button @click="visible = false">取消</el-button>
       <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
     </span>
  </el-dialog>
</template>

<script>
  import {lotattConfigList} from "@/api/ams/config"
  import {addDocAsnDetails, queryDocAsnDetailsInfo, updateDocAsnDetails} from "@/api/docAsn/docAsn"
  import SkuSearchDialog from '@/views/base/baseSku/skuSearchDialog'
  import {queryLocationZoneDict} from "@/api/base/locationZone"
  import {queryLocationDict} from "@/api/base/locationInfo"

  export default {
    dicts: ['sys_yes_no', 'ams_inv_quality', 'asn_status'],
    data() {
      return {
        visible: false,
        skuSearchDialogVisible: false,
        roleList: [],
        dataForm: {
          asnNo: '',
          asnLineNo: '',
          sku: '',
          expectedQty: null,
          expectedQtyEach: null,
          receivedQty: null,
          receivedQtyEach: null,
          holdRejectreason: '',
          notes: '',
          locationZone: '',
          locationId: '',
          packId: '',
          lineStatus: '00',
          lotatt01: '',
          lotatt02: '',
          lotatt03: '',
          lotatt04: '',
          lotatt05: '',
          lotatt06: '',
          lotatt07: '',
          lotatt08: '',
          lotatt09: '',
          lotatt10: '',
          lotatt11: '',
          lotatt12: '',
          lotatt13: '',
          lotatt14: '',
          lotatt15: '',
          lotatt16: '',
          lotatt17: '',
          lotatt18: ''
        },
        dataRule: {},
        tableOption: [],
        attShowFlag: false,
        locationZoneCombo: [],
        locationCombo: [],
        isAdd: true
      }
    },
    components: {
      SkuSearchDialog
    },
    methods: {
      init(asnNo,asnLineNo) {
        if(asnLineNo != ''){
          this.isAdd = false
        } else {
          this.isAdd = true
        }
        this.dataForm.asnNo = asnNo
        this.dataForm.asnLineNo = ''
        this.dataForm.sku = ''
        this.dataForm.expectedQty = 0
        this.dataForm.locationZone = ''
        this.dataForm.locationId = ''
        this.dataForm.packId = ''
        this.dataForm.lineStatus = '00'
        this.initData()
        this.visible = true
        if(asnNo && asnLineNo) {
          this.$nextTick(() => {
            //查询明细
            queryDocAsnDetailsInfo(asnNo,asnLineNo).then().then(response => {
              this.dataForm.asnNo = response.data.asnNo
              this.dataForm.asnLineNo = response.data.asnLineNo
              this.dataForm.sku = response.data.sku
              this.dataForm.expectedQty = response.data.expectedQty
              if(response.data.locationZone) {
                this.dataForm.locationZone = parseInt(response.data.locationZone)
                this.locationZoneChange(this.dataForm.locationZone)
              }
              if(response.data.locationId) {
                this.dataForm.locationId = parseInt(response.data.locationId)
              }
              this.dataForm.lineStatus = response.data.lineStatus
            });
          })
        }
      },
      // 表单提交
      dataFormSubmit() {
        this.$refs["dataForm"].validate(valid => {
          if (valid) {
            if(this.isAdd) {
              for (let i = 0; i < this.tableOption.length; i++) {
                this.dataForm['' + this.tableOption[i].lotattId] = this.tableOption[i].defaultValue
              }
              addDocAsnDetails(this.dataForm).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.visible = false
                this.$emit('refreshDataList',this.dataForm.asnNo)
              });
            } else {
              updateDocAsnDetails(this.dataForm).then(response => {
                this.$modal.msgSuccess("更新成功");
                this.visible = false
                this.$emit('refreshDataList',this.dataForm.asnNo)
              });
            }
          }
        });
      },
      initData() {
        this.tableOption.length = 0
        lotattConfigList().then(response => {
          this.tableOption = response.data
          if(this.tableOption && this.tableOption.length > 0) {
            for(let i = 0;i < this.tableOption.length; i++) {
              //单据不要必填项
              this.tableOption[i].lotattFlag = 'Choice'
              this.tableOption[i].defaultValue = ''
            }
          }
        })

        queryLocationZoneDict().then(response => {
          this.locationZoneCombo = response.data
        })

      },
      showHidden() {
        this.attShowFlag = !this.attShowFlag
      },
      handleSkuIconClick() {
        this.skuSearchDialogVisible = true
        this.$nextTick(() => {
          this.$refs.skuSearch.init()
        })
      },
      afterChoseSku: function (userName, value) {
        this.dataForm.sku = value
      },
      locationZoneChange: function (value) {
        let param = 0
        if (value != '') {
          param = value
        }
        queryLocationDict(param).then(response => {
          this.locationCombo = response.data
        })
      }
    }
  }
</script>
