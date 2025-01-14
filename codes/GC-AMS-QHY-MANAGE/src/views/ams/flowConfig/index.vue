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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['ams:flowConfig:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ams:flowConfig:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['ams:flowConfig:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ams:flowConfig:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="flowConfigList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="流程名称" align="center" prop="flowName" />
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
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ams:flowConfig:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ams:flowConfig:remove']"
          >删除</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="流程名称" prop="flowName">
              <el-input v-model="form.flowName" placeholder="请输入流程名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作类型" prop="flowType">
              <el-select style="width: 100%" v-model="form.flowType" placeholder="请选操作类型" clearable size="small">
                <el-option
                  v-for="dict in flowTypeDict"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入内容" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料选择范围" prop="skuType">
              <el-select style="width: 100%" v-model="form.skuType" :multiple="true" placeholder="请选择物料种类" clearable size="small">
                <el-option
                  v-for="dict in skuTypeCombo"
                  :key="dict.itemCode"
                  :label="dict.itemName"
                  :value="dict.itemCode"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否显示" prop="skuTypeFlag">
              <el-select style="width: 100%" v-model="form.skuTypeFlag" placeholder="是否显示" size="small">
                <el-option
                  v-for="dict in inputTagCombo"
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
            <el-form-item label="数量范围" prop="qtyRange">
              <el-input v-model="form.qtyRange" placeholder="数据格式:1-5" @change="qtyRangeChange"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否显示" prop="qtyFlag">
              <el-select style="width: 100%" v-model="form.qtyFlag" placeholder="是否显示" size="small">
                <el-option
                  v-for="dict in inputTagCombo"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="默认值" prop="qty">
              <el-input v-model="form.qty" placeholder="请输入数量" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="重量范围" prop="weightRange">
              <el-input v-model="form.weightRange" placeholder="数据格式:1-5" @change="weightRangeChange"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否显示" prop="weightFlag">
              <el-select style="width: 100%" v-model="form.weightFlag" placeholder="是否显示" size="small">
                <el-option
                  v-for="dict in inputTagCombo"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="默认值" prop="weight">
              <el-input v-model="form.weight" placeholder="请输入重量" type="number"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!--<el-row>
          <el-col :span="8">
            <el-form-item label="供应商范围" prop="supplierRange">
              <el-select style="width: 100%" v-model="form.supplierRange" :multiple="true" placeholder="请选择供应商范围" clearable size="small">
                <el-option
                  v-for="dict in supplierCombo"
                  :key="dict.supplierId"
                  :label="dict.supplierName"
                  :value="dict.supplierId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否显示" prop="supplierFlag">
              <el-select style="width: 100%" v-model="form.supplierFlag" placeholder="是否显示" size="small">
                <el-option
                  v-for="dict in inputTagCombo"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="默认值" prop="supplier">
              <el-select style="width: 100%" v-model="form.supplier" placeholder="请选择供应商" clearable size="small">
                <el-option
                  v-for="dict in supplierCombo"
                  :key="dict.supplierId"
                  :label="dict.supplierName"
                  :value="dict.supplierId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>-->
        <el-row>
          <el-col :span="10">
            <el-form-item label="起点范围" prop="locationFrom">
              <treeselect
                :multiple="true"
                v-model="form.locationFrom"
                :options="locationFromOptions"
                :value-consists-of="'BRANCH_PRIORITY'"
                :filter-node-method="filterNode"
                :show-count="true" placeholder="请选择起点范围" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否显示" prop="locationFromFlag">
              <el-select style="width: 100%" v-model="form.locationFromFlag" placeholder="是否显示" size="small">
                <el-option
                  v-for="dict in inputTagCombo"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="启用策略" prop="locationFromStrategyFlag">
              <el-select style="width: 100%" v-model="form.locationFromStrategyFlag" size="small">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="终点范围" prop="locationTo">
              <treeselect
                :multiple="true"
                v-model="form.locationTo"
                :options="locationToOptions"
                :value-consists-of="'BRANCH_PRIORITY'"
                :filter-node-method="filterNode"
                :show-count="true" placeholder="请选择终点范围" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="是否显示" prop="locationToFlag">
              <el-select style="width: 100%" v-model="form.locationToFlag" placeholder="是否显示" size="small">
                <el-option
                  v-for="dict in inputTagCombo"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="启用策略" prop="locationToStrategyFlag">
              <el-select style="width: 100%" v-model="form.locationToStrategyFlag" size="small">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="关联流程" prop="relFlow">
              <el-select style="width: 100%" v-model="form.relFlow" size="small" clearable>
                <el-option
                  v-for="dict in flowDict"
                  :key="dict.id"
                  :label="dict.flowName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="16">
          </el-col>
        </el-row>
        <el-divider content-position="center">事件配置</el-divider>
        <div><el-button type="primary" @click="addEvent">添加配置</el-button></div>
        <div>
          <el-table :data="eventData" ref="eventTables">
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="delEvent(scope.$index)"
                  v-hasPermi="['ams:flowConfig:edit']"
                >删除</el-button>
              </template>
            </el-table-column>
            <el-table-column label="NDC事件" prop="eventCode">
              <template slot-scope="scope">
                <el-input type="text" v-model="scope.row.eventCode" class="cell-input"/>
              </template>
            </el-table-column>
            <el-table-column label="请求地址" prop="accessUrl">
              <template slot-scope="scope">
                <el-input type="text" v-model="scope.row.accessUrl" class="cell-input"/>
              </template>
            </el-table-column>
            <el-table-column label="请求参数" prop="accessParam">
              <template slot-scope="scope">
                <el-input type="text" v-model="scope.row.accessParam" class="cell-input"/>
              </template>
            </el-table-column>
            <el-table-column label="回调地址" prop="callBackUrl">
              <template slot-scope="scope">
                <el-input type="text" v-model="scope.row.callbackUrl" class="cell-input"/>
              </template>
            </el-table-column>
            <el-table-column label="回调参数" prop="callBackParam">
              <template slot-scope="scope">
                <el-input type="text" v-model="scope.row.callbackParam" class="cell-input"/>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <div style="cursor: pointer;" @click="showHidden">
          <el-divider content-position="center">批次属性信息</el-divider>
        </div>
        <el-table v-show="attShowFlag" :data="flowConfigDetailsList" :row-class-name="rowFlowConfigDetailsIndex" @selection-change="handleFlowConfigDetailsSelectionChange" ref="flowConfigDetails">
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="批次属性" prop="lotattId">
            <template slot-scope="scope">
              {{scope.row.lotattName}}
            </template>
          </el-table-column>
          <el-table-column label="是否显示" prop="lotattFlag">
            <template slot-scope="scope">
              <el-select style="width: 100%" v-model="scope.row.lotattFlag" placeholder="是否显示" clearable size="small">
                <el-option
                  v-for="dict in inputTagCombo"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="默认值">
            <template slot-scope="scope">
              <el-input type="text" v-model="scope.row.defaultValue" class="cell-input"/>
            </template>
          </el-table-column>
          <el-table-column label="备注" prop="remark"></el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFlowConfig, getFlowConfig, delFlowConfig, addFlowConfig, updateFlowConfig, queryLotattConfigListActive, flowConfigDict } from "@/api/ams/flowConfig";
import { querySkuTypeDict } from "@/api/base/skuType";
import { treeselectLocationZone } from "@/api/base/locationZone";
import { querySupplierDict } from "@/api/base/supplier";
import { treeselect } from "@/api/base/locationInfo";
import { inputTag } from "@/utils/combo/dict";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "FlowConfig",
  components: { Treeselect },
  dicts: ['sys_yes_no'],
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
      rules: {
        flowType: [
          { required: true, message: "操作类型不能为空", trigger: "blur" }
        ],
        flowName: [
          { required: true, message: "流程名称不能为空", trigger: "blur" }
        ],
        locationFrom: [
          { required: true, message: "起点范围不能为空", trigger: "blur" }
        ],
        locationTo: [
          { required: true, message: "终点范围不能为空", trigger: "blur" }
        ]
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      inputTagCombo: inputTag,
      supplierCombo: [],
      skuTypeCombo: undefined,
      locationFromOptions: undefined,
      locationToOptions: undefined,
      qtyStart: null,
      qtyEnd: null,
      weightStart: null,
      weightEnd: null,
      flowTypeDict: [
        {label : '收货', value : 'RECEIPT'},
        {label : 'AGV入库上架', value : 'ASN'},
        {label : '标准入库上架', value : 'BASASN'},
        {label : 'AGV拣货出库', value : 'SO'},
        {label : 'AGV按单出库', value : 'BATCHSO'},
        {label : '移库', value : 'MV'}
      ],
      flowDict: [],
      attShowFlag: true,
      eventData: []
    };
  },
  created() {
    this.init();
    this.getList();
  },
  methods: {
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
        flowType: null,
        skuType: null,
        skuTypeFlag: 'Choice',
        qty: null,
        qtyRange: null,
        qtyFlag: 'Choice',
        weight: null,
        weightRange: null,
        weightFlag: 'Choice',
        supplier: null,
        supplierRange: null,
        supplierFlag: 'Choice',
        locationFrom: null,
        locationFromFlag: 'Choice',
        locationFromDevice: null,
        locationFromStrategyFlag: 'N',
        locationTo: null,
        locationToFlag: 'Choice',
        locationToDevice: null,
        locationToStrategyFlag: 'N',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
        relFlow: '',
        rootFlow: ''
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程配置";
      // 加载批次属性明细
      queryLotattConfigListActive(this.queryParams).then(response => {
        this.flowConfigDetailsList = response.data;
      });

    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      //查询流程配置字典
      flowConfigDict(id).then(response => {
        this.flowDict = response.data;
      });
      getFlowConfig(id).then(response => {
        this.form = response.data;
        this.flowConfigDetailsList = response.data.flowConfigDetailsList;
        this.eventData = response.data.flowConfigEventList;
        this.qtyRangeChange(this.form.qtyRange)
        this.weightRangeChange(this.form.weightRange)
        this.open = true;
        this.title = "修改流程配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if (this.form.qty) {
        if (this.qtyStart > this.form.qty) {
          this.$modal.msgError("数量默认值小于范围");
          return
        }

        if (this.qtyEnd < this.form.qty) {
          this.$modal.msgError("数量默认值大于范围");
          return
        }
      }

      if (this.form.weight) {
        if (this.weightStart > this.form.weight) {
          this.$modal.msgError("重量默认值小于范围");
          return false
        }

        if (this.weightEnd < this.form.weight) {
          this.$modal.msgError("重量默认值大于范围");
          return false
        }
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.flowConfigDetailsList = this.flowConfigDetailsList;
          this.form.flowConfigEventList = this.eventData;
          if (this.form.id != null) {
            updateFlowConfig(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFlowConfig(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除流程配置编号为"' + ids + '"的数据项？').then(function() {
        return delFlowConfig(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
	/** 流程配置体序号 */
    rowFlowConfigDetailsIndex({ row, rowIndex }) {
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
        this.flowConfigDetailsList = flowConfigDetailsList.filter(function(item) {
          return checkedFlowConfigDetails.indexOf(item.index) == -1
        });
      }
    },
    /** 复选框选中数据 */
    handleFlowConfigDetailsSelectionChange(selection) {
      this.checkedFlowConfigDetails = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ams/flowConfig/export', {
        ...this.queryParams
      }, `flowConfig_${new Date().getTime()}.xlsx`)
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    qtyRangeChange(e) {
      // 范围变更检测
      if (e !== '' && e !== null) {
        let r = /^\d+(\.\d+)?$/　　//小数
        let flag = r.test(e);
        if (flag) {
          this.form.qtyRange = '1-' + e
          this.qtyStart = 1
          this.qtyEnd = e
        } else {
          let arr = e.split('-')
          this.qtyStart = arr[0]
          this.qtyEnd = arr[1]
        }
      }
    },
    weightRangeChange(e) {
      // 范围变更检测
      if (e !== '' && e !== null) {
        let r = /^\d+(\.\d+)?$/　　//小数
        let flag = r.test(e);
        if (flag) {
          this.form.weightRange = '1-' + e
          this.weightStart = 1
          this.weightEnd = e
        } else {
          let arr = e.split('-')
          this.weightStart = arr[0]
          this.weightEnd = arr[1]
        }
      }
    },
    init() {
      // 初始化物料分类
      querySkuTypeDict().then(response => {
        this.skuTypeCombo = response.data;
      });

      // 初始化供应商
      querySupplierDict().then(response => {
        this.supplierCombo = response.data;
      });

      // 初始化库位
      treeselectLocationZone().then(response => {
        this.locationFromOptions = response.data;
        this.locationToOptions = response.data;
      });
    },
    showHidden() {
      this.attShowFlag = !this.attShowFlag
    },
    addEvent() {
      let obj = {}
      obj.eventCode = ''
      obj.accessUrl = ''
      obj.accessParam = ''
      obj.callBackUrl = ''
      obj.callBackParam = ''
      this.eventData.push(obj)
    },
    delEvent(index) {
      this.eventData.splice(index, 1)
    }
  }
};
</script>
