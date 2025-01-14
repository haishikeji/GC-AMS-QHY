<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="主键" prop="allocationId">
        <el-input
          v-model="queryParams.allocationId"
          placeholder="请输入主键"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出库单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入出库单号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.so_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <!--<el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['ams:allocation:add']"
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
          v-hasPermi="['ams:allocation:edit']"
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
          v-hasPermi="['ams:allocation:remove']"
        >删除</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ams:allocation:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="allocationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="主键" align="center" prop="allocationId" />-->
      <el-table-column label="出库单号" align="center" prop="orderNo" />
      <el-table-column label="行号" align="center" prop="lineNo" />
      <el-table-column label="物料" align="center" prop="material" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.so_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="分配数量" align="center" prop="qty" />
      <el-table-column label="拣货数量" align="center" prop="pickQty" />
      <el-table-column label="已拣库位" align="center" prop="pickToLocation" />
      <el-table-column label="拣货事务" align="center" prop="pickTransactionId" />
      <el-table-column
        v-for="(item, index) in tableOption"
        :key="index"
        :label="item.label"
        align="center" :prop="item.prop" width="200">
      </el-table-column>
      <el-table-column label="lotnum" align="center" prop="lotnum" />
      <!--<el-table-column label="待检库位" align="center" prop="locationId" />
      <el-table-column label="叫料待检事务id" align="center" prop="callTransactionId" />-->
      <el-table-column label="创建时间" align="center" prop="createDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!--<el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['ams:allocation:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['ams:allocation:remove']"
          >删除</el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改分配查询对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="出库单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入出库单号" />
        </el-form-item>
        <el-form-item label="行号" prop="lineNo">
          <el-input v-model="form.lineNo" placeholder="请输入行号" />
        </el-form-item>
        <el-form-item label="物料" prop="material">
          <el-input v-model="form.material" placeholder="请输入物料" />
        </el-form-item>
        <el-form-item label="待检库位" prop="locationId">
          <el-input v-model="form.locationId" placeholder="请输入待检库位" />
        </el-form-item>
        <el-form-item label="叫料待检事务id" prop="callTransactionId">
          <el-input v-model="form.callTransactionId" placeholder="请输入叫料待检事务id" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.so_status"
              :key="dict.value"
:label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="lotnum" prop="lotnum">
          <el-input v-model="form.lotnum" placeholder="请输入lotnum" />
        </el-form-item>
        <el-form-item label="拣货数量" prop="qty">
          <el-input v-model="form.qty" placeholder="请输入拣货数量" />
        </el-form-item>
        <el-form-item label="拣货事务id" prop="pickTransactionId">
          <el-input v-model="form.pickTransactionId" placeholder="请输入拣货事务id" />
        </el-form-item>
        <el-form-item label="拣货库位" prop="pickToLocation">
          <el-input v-model="form.pickToLocation" placeholder="请输入拣货库位" />
        </el-form-item>
        <el-form-item label="${comment}" prop="createUserId">
          <el-input v-model="form.createUserId" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="createDate">
          <el-date-picker clearable size="small"
            v-model="form.createDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择${comment}">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="${comment}" prop="editUserId">
          <el-input v-model="form.editUserId" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="editDate">
          <el-date-picker clearable size="small"
            v-model="form.editDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择${comment}">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="${comment}" prop="userdefine1">
          <el-input v-model="form.userdefine1" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="userdefine2">
          <el-input v-model="form.userdefine2" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="userdefine3">
          <el-input v-model="form.userdefine3" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="${comment}" prop="userdefine4">
          <el-input v-model="form.userdefine4" placeholder="请输入${comment}" />
        </el-form-item>
        <el-form-item label="拣货数量" prop="pickQty">
          <el-input v-model="form.pickQty" placeholder="请输入拣货数量" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAllocation, getAllocation, delAllocation, addAllocation, updateAllocation } from "@/api/ams/allocation";
import {lotattConfigList} from "@/api/ams/config";
import {queryLocationZoneDict} from "@/api/base/locationZone";

export default {
  name: "Allocation",
  dicts: ['so_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 分配查询表格数据
      allocationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      dateRange: [],
      tableOption: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        allocationId: null,
        orderNo: null,
        status: null,
        createDate: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "出库单号不能为空", trigger: "blur" }
        ],
        lineNo: [
          { required: true, message: "行号不能为空", trigger: "blur" }
        ],
        material: [
          { required: true, message: "物料不能为空", trigger: "blur" }
        ],
        locationId: [
          { required: true, message: "待检库位不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.initData();
    this.getList();
  },
  methods: {
    initData() {
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
    /** 查询分配查询列表 */
    getList() {
      this.loading = true;
      listAllocation(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.allocationList = response.rows;
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
        allocationId: null,
        orderNo: null,
        lineNo: null,
        material: null,
        locationId: null,
        callTransactionId: null,
        status: "0",
        lotnum: null,
        qty: null,
        pickTransactionId: null,
        pickToLocation: null,
        createUserId: null,
        createDate: null,
        editUserId: null,
        editDate: null,
        userdefine1: null,
        userdefine2: null,
        userdefine3: null,
        userdefine4: null,
        pickQty: null
      };
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
      this.ids = selection.map(item => item.allocationId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加分配查询";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const allocationId = row.allocationId || this.ids
      getAllocation(allocationId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改分配查询";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.allocationId != null) {
            updateAllocation(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAllocation(this.form).then(response => {
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
      const allocationIds = row.allocationId || this.ids;
      this.$modal.confirm('是否确认删除分配查询编号为"' + allocationIds + '"的数据项？').then(function() {
        return delAllocation(allocationIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ams/allocation/export', {
        ...this.queryParams
      }, `allocation_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
