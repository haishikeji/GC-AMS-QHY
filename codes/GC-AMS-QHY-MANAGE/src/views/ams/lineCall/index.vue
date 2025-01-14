<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="flowName">
        <el-input
          v-model="queryParams.flowName"
          placeholder="请输入流程名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="flowConfigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="编号" align="center" prop="id"/>
      <el-table-column label="流程名称" align="center" prop="flowName"/>
      <!--<el-table-column label="物料类型" align="center" prop="skuType" />
      <el-table-column label="物料类型是否显示" align="center" prop="skuTypeFlag" />
      <el-table-column label="数量" align="center" prop="qty" />
      <el-table-column label="数量是否显示" align="center" prop="qtyFlag" />
      <el-table-column label="重量" align="center" prop="weight" />
      <el-table-column label="重量是否显示" align="center" prop="weightFlag" />
      <el-table-column label="供应商" align="center" prop="supplier" />
      <el-table-column label="供应商是否显示" align="center" prop="supplierFlag" />
      <el-table-column label="起点范围" align="center" prop="locationFrom" />
      <el-table-column label="起点是否对接设备" align="center" prop="locationFromDevice" />
      <el-table-column label="终点范围" align="center" prop="locationTo" />
      <el-table-column label="终点是否对接设备" align="center" prop="locationToDevice" />-->
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >选择
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改流程配置头对话框 -->
    <el-dialog ref="digEdit" :title="title" :visible.sync="open" width="700px" append-to-body @open="digOpen" destroy-on-close @close="digClose">
      <el-form ref="relForm" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="起点位置" prop="locationFrom" v-if="form.locationFromFlag !== 'Hidden'">
          <el-select style="width: 100%" v-model="form.locationFrom" placeholder="请选择起点位置" clearable size="small">
            <el-option
              v-for="dict in locationFromCombo"
              :key="dict.id"
              :label="dict.locationNo"
              :value="dict.id"
            >
              <div>{{ dict.id == dict.zoneId ? dict.locationNo : dict.locationNo + '  ' + (dict.isEmpty == 'Y' ? '' : '*') }}</div>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="物料" prop="sku" v-if="form.skuTypeFlag !== 'Hidden'">
          <el-select style="width: 100%" v-model="form.sku" placeholder="请选择物料" clearable size="small">
            <el-option
              v-for="dict in skuTypeCombo"
              :key="dict.sku"
              :label="dict.sku + '-' + dict.desc1"
              :value="dict.sku"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="qty" v-if="form.qtyFlag !== 'Hidden'">
          <el-input v-model="form.qty" :placeholder="`输入范围${form.qtyMin == null ? '' : form.qtyMin + '-' + form.qtyMax}`" type="number"/>
        </el-form-item>
        <el-form-item label="重量" prop="weight" v-if="form.weightFlag !== 'Hidden'">
          <el-input v-model="form.weight" :placeholder="`输入范围${form.weightMin == null ? '' : form.weightMin + '-' + form.weightMax}`" type="number"/>
        </el-form-item>
        <!--<el-form-item label="供应商" prop="supplier" v-if="form.supplierFlag !== 'Hidden'">
          <el-select style="width: 100%" v-model="form.supplier" placeholder="请选择供应商" clearable size="small">
            <el-option
              v-for="dict in supplierCombo"
              :key="dict.supplierId"
              :label="dict.supplierName"
              :value="dict.supplierId"
            />
          </el-select>
        </el-form-item>-->
        <div style="cursor: pointer;" @click="showHidden">
          <el-divider content-position="center">批次属性信息...</el-divider>
        </div>
        <el-table v-show="attShowFlag" :data="flowConfigDetailsList" :row-class-name="rowFlowConfigDetailsIndex"
                  @selection-change="handleFlowConfigDetailsSelectionChange" ref="flowConfigDetails">
          <el-table-column label="序号" align="center" prop="index" width="50"/>
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
                <el-date-picker v-model="scope.row.defaultValue" type="date" placeholder="" style="width: 100%" value-format="yyyy-MM-dd"/>
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
        <el-form-item label="终点位置" prop="locationTo" v-if="form.locationToFlag !== 'Hidden'">
          <el-select style="width: 100%" v-model="form.locationTo" placeholder="请选择终点位置" clearable size="small">
            <el-option
              v-for="dict in locationToCombo"
              :key="dict.id"
              :label="dict.locationNo"
              :value="dict.id"
            >
              <div>{{ dict.id == dict.zoneId ? dict.locationNo : dict.locationNo + '  ' + (dict.isEmpty == 'Y' ? '' : '*') }}</div>
            </el-option>
          </el-select>
        </el-form-item>
        <div style="cursor: pointer;">
          <el-divider content-position="center">呼叫明细...</el-divider>
        </div>
        <el-table :data="subData" :row-class-name="rowFlowConfigDetailsIndex"
                  @selection-change="handleFlowConfigDetailsSelectionChange" ref="flowConfigDetails">
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="delDetail(scope.$index)"
                v-hasPermi="['ams:flowConfig:edit']"
              >删除</el-button>
            </template>
          </el-table-column>
          <el-table-column label="物料" prop="sku" width="200"/>
          <el-table-column label="数量" prop="qty"/>
          <el-table-column label="重量" prop="weight"/>
          <el-table-column
            v-for="(item, index) in tableOption"
            :key="index"
            :label="item.label"
            align="center" :prop="item.prop" width="200">
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addDetail">添 加</el-button>
        <el-button type="primary" @click="submitForm">执 行</el-button>
        <el-button type="primary" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
  .border-req {
    border:1px solid #F00
  }
</style>

<script>
  import {
    listFlowConfig,
    getFlowConfig,
    updateFlowConfig,
    listLotattConfig,
    flowConfigLotatt
  } from "@/api/ams/flowConfig";
  import {choseFlow, exec} from "@/api/ams/lineCall";
  import {querySkuDict} from "@/api/base/baseSku";
  import {querySupplierDict} from "@/api/base/supplier";
  import {treeselect} from "@/api/base/locationInfo";
  import {inputTag} from "@/utils/combo/dict";
  import { lotattConfigList } from "@/api/ams/config";

  export default {
    name: "FlowConfig",
    dicts: ['sys_yes_no', 'ams_inv_quality'],
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 子表选中数据
        checkedFlowConfigDetails: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 流程配置头表格数据
        flowConfigList: [],
        // 流程配置体表格数据
        flowConfigDetailsList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          flowName: null,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {},
        defaultProps: {
          children: "children",
          label: "label"
        },
        inputTagCombo: inputTag,
        supplierCombo: [],
        skuTypeCombo: undefined,
        locationFromOptions: undefined,
        locationToOptions: undefined,
        locationFromCombo: undefined,
        locationToCombo: undefined,
        attShowFlag: false,
        tableOption: [],
        subData: [],
        showAddDetails: true
      };
    },
    created() {
      this.initLotatt();
      this.init();
      this.getList();
    },
    methods: {
      initLotatt() {
        lotattConfigList().then(response => {
          let data = response.data
          for(let i=0;i<data.length;i++) {
            let obj = new Object();
            obj.label = data[i].lotattName
            obj.prop = data[i].lotattId
            this.tableOption.push(obj)
          }
        })
      },
      /** 查询流程配置头列表 */
      getList() {
        this.loading = true;
        listFlowConfig(this.queryParams).then(response => {
          this.flowConfigList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          id: null,
          flowName: null,
          sku: '',
          skuTypeFlag: 'Choice',
          qty: null,
          qtyFlag: 'Choice',
          qtyMin: null,
          qtyMax: null,
          weight: null,
          weightFlag: 'Choice',
          weightMin: null,
          weightMax: null,
          supplier: '',
          supplierFlag: 'Choice',
          locationFrom: null,
          locationFromFlag: null,
          locationTo: null,
          locationToFlag: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          remark: null,
          lineCallDetailsFormList: null,
          flowType: null
        };
        this.flowConfigDetailsList = [];
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加流程配置";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const id = row.id || this.ids
        this.subData = []
        // 加载批次属性明细
        flowConfigLotatt(id).then(response => {
          this.flowConfigDetailsList = response.data;
        });
        choseFlow(id).then(response => {
          this.locationFromCombo = response.locationFrom
          this.locationToCombo = response.locationTo
          this.form.skuTypeFlag = response.skuTypeFlag
          this.form.qtyFlag = response.qtyFlag
          this.form.qty = response.qty
          this.form.qtyMin = response.qtyMin
          this.form.qtyMax = response.qtyMax
          this.form.weightFlag = response.weightFlag
          this.form.weight = response.weight
          this.form.weightMin = response.weightMin
          this.form.weightMax = response.weightMax
          this.form.supplierFlag = response.supplierFlag
          this.form.locationFromFlag = response.locationFromFlag
          this.form.locationToFlag = response.locationToFlag
          this.form.flowType = response.flowType
          this.skuTypeCombo = response.skuList;
          this.form.id = id

          if (this.form.locationFromFlag === 'Required') {
            this.rules.locationFrom = [
              {required: true, message: "*", trigger: "blur"}
            ]
          }

          if (this.form.skuTypeFlag === 'Required') {
            this.rules.sku = [
              {required: true, message: "*", trigger: "blur"}
            ]
          }

          if (this.form.qtyFlag === 'Required') {
            this.rules.qty = [
              {required: true, message: "*", trigger: "blur"}
            ]
          }

          if (this.form.weightFlag === 'Required') {
            this.rules.weight = [
              {required: true, message: "*", trigger: "blur"}
            ]
          }

          if (this.form.supplierFlag === 'Required') {
            this.rules.supplier = [
              {required: true, message: "*", trigger: "blur"}
            ]
          }

          if (this.form.locationToFlag === 'Required') {
            this.rules.locationTo = [
              {required: true, message: "*", trigger: "blur"}
            ]
          }

          if (this.form.flowType === 'ASN') {
            this.showAddDetails = true
          } else {
            this.showAddDetails = false
          }

          this.open = true;
          this.title = row.flowName;
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["relForm"].validate(valid => {
          if (valid) {
            if (this.form.skuTypeFlag === 'Required' && this.form.skuType === '') {
              this.$modal.msgError("物料必须选择");
              return
            }
            if (this.form.qtyFlag === 'Required' && this.form.qty === null) {
              this.$modal.msgError("数量必须输入");
              return
            } else if (this.form.qtyFlag === 'Required' && (this.form.qtyMin != null || this.form.qtyMax != null)){
              if(this.form.qty < this.form.qtyMin) {
                this.$modal.msgError("数量小于范围:" + this.form.qtyMin);
                return
              }
              if(this.form.qty > this.form.qtyMax) {
                this.$modal.msgError("数量大于范围:" + this.form.qtyMax);
                return
              }
            }
            if (this.form.weightFlag === 'Required' && this.form.weight === null) {
              this.$modal.msgError("重量必须输入");
              return
            } else if (this.form.weightFlag === 'Required' && (this.form.weightMin != null || this.form.weightMax != null)) {
              if(this.form.weight < this.form.weightMin) {
                this.$modal.msgError("重量小于范围:" + this.form.weightMin);
                return
              }
              if(this.form.weight > this.form.weightMax) {
                this.$modal.msgError("重量大于范围:" + this.form.weightMax);
                return
              }
            }
            if (this.form.supplierFlag === 'Required' && this.form.supplier === null) {
              this.$modal.msgError("供应商必须输入");
              return
            }
            if (this.form.locationFromFlag === 'Required' && this.form.locationFrom === null) {
              this.$modal.msgError("必须选择起点位置");
              return
            }
            if (this.form.locationToFlag === 'Required' && this.form.locationTo === null) {
              this.$modal.msgError("必须选择终点位置");
              return
            }

            if (this.flowConfigDetailsList.length > 0) {
              for (let i = 0; i < this.flowConfigDetailsList.length; i++) {
                if (this.flowConfigDetailsList[i].lotattFlag === 'Required') {
                  if(this.flowConfigDetailsList[i].defaultValue === '' || this.flowConfigDetailsList[i].defaultValue === null) {
                    this.$modal.msgError(this.flowConfigDetailsList[i].lotattName+"必须输入");
                    return
                  }
                }
              }
            }
            let flowIdCurrent = this.form.id
            if(this.subData.length == 0) {
              this.addDetail()
            }
            let subParam = {}
            subParam.lineCallList = this.subData
            subParam.flowId = flowIdCurrent
            exec(subParam).then(response => {
              if (response.code === 200) {
                this.$modal.msgSuccess("执行成功");
                this.open = false;
                this.subData.length = 0
              } else {
                this.subData.length = 0
                this.$modal.msgError(response.msg);
              }
            });
          }
        });
      },
      /** 流程配置体序号 */
      rowFlowConfigDetailsIndex({row, rowIndex}) {
        row.index = rowIndex + 1;
      },
      /** 流程配置体添加按钮操作 */
      handleAddFlowConfigDetails() {
        let obj = {};
        obj.lotattId = "";
        obj.lotattFlag = "";
        obj.remark = "";
        this.flowConfigDetailsList.push(obj);
      },
      /** 流程配置体删除按钮操作 */
      handleDeleteFlowConfigDetails() {
        if (this.checkedFlowConfigDetails.length == 0) {
          this.$modal.msgError("请先选择要删除的流程配置体数据");
        } else {
          const flowConfigDetailsList = this.flowConfigDetailsList;
          const checkedFlowConfigDetails = this.checkedFlowConfigDetails;
          this.flowConfigDetailsList = flowConfigDetailsList.filter(function (item) {
            return checkedFlowConfigDetails.indexOf(item.index) == -1
          });
        }
      },
      /** 复选框选中数据 */
      handleFlowConfigDetailsSelectionChange(selection) {
        this.checkedFlowConfigDetails = selection.map(item => item.index)
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      init() {
        // 初始化供应商
        querySupplierDict().then(response => {
          this.supplierCombo = response.data;
        });
      },
      showHidden () {
        this.attShowFlag = !this.attShowFlag
      },
      digOpen () {
        this.$nextTick( () => {
          this.$refs.relForm.validate();
        })
      },
      digClose () {
        this.rules = {}
      },
      addDetail () {
        this.$refs["relForm"].validate(valid => {
          if (valid) {
            if (this.form.skuTypeFlag === 'Required' && this.form.skuType === '') {
              this.$modal.msgError("物料必须选择");
              return
            }
            if (this.form.qtyFlag === 'Required' && this.form.qty === null) {
              this.$modal.msgError("数量必须输入");
              return
            } else if (this.form.qtyFlag === 'Required' && (this.form.qtyMin != null || this.form.qtyMax != null)) {
              if (this.form.qty < this.form.qtyMin) {
                this.$modal.msgError("数量小于范围:" + this.form.qtyMin);
                return
              }
              if (this.form.qty > this.form.qtyMax) {
                this.$modal.msgError("数量大于范围:" + this.form.qtyMax);
                return
              }
            }
            if (this.form.weightFlag === 'Required' && this.form.weight === null) {
              this.$modal.msgError("重量必须输入");
              return
            } else if (this.form.weightFlag === 'Required' && (this.form.weightMin != null || this.form.weightMax != null)) {
              if (this.form.weight < this.form.weightMin) {
                this.$modal.msgError("重量小于范围:" + this.form.weightMin);
                return
              }
              if (this.form.weight > this.form.weightMax) {
                this.$modal.msgError("重量大于范围:" + this.form.weightMax);
                return
              }
            }
            if (this.form.supplierFlag === 'Required' && this.form.supplier === null) {
              this.$modal.msgError("供应商必须输入");
              return
            }
            if (this.form.locationFromFlag === 'Required' && this.form.locationFrom === null) {
              this.$modal.msgError("必须选择起点位置");
              return
            }
            if (this.form.locationToFlag === 'Required' && this.form.locationTo === null) {
              this.$modal.msgError("必须选择终点位置");
              return
            }
            if (this.flowConfigDetailsList.length > 0) {
              for (let i = 0; i < this.flowConfigDetailsList.length; i++) {
                if (this.flowConfigDetailsList[i].lotattFlag === 'Required') {
                  if(this.flowConfigDetailsList[i].defaultValue === '' || this.flowConfigDetailsList[i].defaultValue === null) {
                    this.$modal.msgError(this.flowConfigDetailsList[i].lotattName+"必须输入");
                    return
                  }
                }
              }

              let lineCallDetailsFormList = new Array();
              for (let i = 0; i < this.flowConfigDetailsList.length; i++) {
                let obj = new Object();
                obj.lotattId = this.flowConfigDetailsList[i].lotattId
                obj.lotattValue = this.flowConfigDetailsList[i].defaultValue
                lineCallDetailsFormList.push(obj)
              }
              this.form.lineCallDetailsFormList = lineCallDetailsFormList
            }
            let obj = JSON.parse(JSON.stringify(this.form))
            this.subData.push(obj)
            //清空数据
            // this.form.sku = undefined
            this.form.qty = undefined
            this.form.weight = undefined
            this.form.supplier = undefined
            this.form.locationFrom = undefined
            this.form.locationTo = undefined
            /*if (this.flowConfigDetailsList.length > 0) {
              for (let i = 0; i < this.flowConfigDetailsList.length; i++) {
                console.log(this.flowConfigDetailsList[i])
                this.flowConfigDetailsList[i].defaultValue = ''
              }
            }*/
          }
        });
      },
      delDetail (index) {
        this.subData.splice(index, 1)
      }
    }
  };
</script>
