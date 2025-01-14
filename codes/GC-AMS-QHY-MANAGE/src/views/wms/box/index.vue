<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="容器编号" prop="boxNo">
        <el-input
          v-model="queryParams.boxNo"
          placeholder="请输入容器编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="容器类型" prop="boxType">
        <el-select v-model="queryParams.boxType" placeholder="请选择容器类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.box_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="boxState">
        <el-select v-model="queryParams.boxState" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.active_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否有货" prop="isEmpty">
        <el-select v-model="queryParams.isEmpty" placeholder="请选择是否有货" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="绑定库位" prop="locationId">
        <el-input
          v-model="queryParams.locationId"
          placeholder="请输入绑定库位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否满托" prop="isFull">
        <el-select v-model="queryParams.isFull" placeholder="请选择是否满托" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['box:boxInfo:add']"
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
          v-hasPermi="['box:boxInfo:edit']"
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
          v-hasPermi="['box:boxInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['box:boxInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="boxInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键" align="center" prop="id" />
      <el-table-column label="容器编号" align="center" prop="boxNo" />
      <el-table-column label="容器类型" align="center" prop="boxType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.box_type" :value="scope.row.boxType"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="boxRemark" />
      <el-table-column label="状态" align="center" prop="boxState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.active_state" :value="scope.row.boxState"/>
        </template>
      </el-table-column>
      <el-table-column label="是否有货" align="center" prop="isEmpty">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isEmpty"/>
        </template>
      </el-table-column>
      <el-table-column label="绑定库位" align="center" prop="locationId" />
      <el-table-column label="是否满托" align="center" prop="isFull">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isFull"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['box:boxInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['box:boxInfo:remove']"
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

    <!-- 添加或修改容器管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="容器编号" prop="boxNo">
          <el-input v-model="form.boxNo" placeholder="请输入容器编号" />
        </el-form-item>
        <el-form-item label="容器类型" prop="boxType">
          <el-select v-model="form.boxType" placeholder="请选择容器类型">
            <el-option
              v-for="dict in dict.type.box_type"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="boxRemark">
          <el-input v-model="form.boxRemark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="状态" prop="boxState">
          <el-select v-model="form.boxState" placeholder="请选择状态">
            <el-option
              v-for="dict in dict.type.active_state"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否无货" prop="isEmpty">
          <el-select v-model="form.isEmpty" placeholder="请选择是否无货">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="绑定库位" prop="locationId">
          <el-input v-model="form.locationId" placeholder="请输入绑定库位" />
        </el-form-item>
        <el-form-item label="是否满托" prop="isFull">
          <el-select v-model="form.isFull" placeholder="请选择是否满托">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
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
import { listBoxInfo, getBoxInfo, delBoxInfo, addBoxInfo, updateBoxInfo } from "@/api/box/boxInfo";

export default {
  name: "BoxInfo",
  dicts: ['sys_yes_no', 'box_type', 'active_state'],
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
      // 容器管理表格数据
      boxInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        boxNo: null,
        boxType: null,
        boxState: null,
        isEmpty: null,
        locationId: null,
        isFull: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询容器管理列表 */
    getList() {
      this.loading = true;
      listBoxInfo(this.queryParams).then(response => {
        this.boxInfoList = response.rows;
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
        boxNo: null,
        boxType: null,
        boxRemark: null,
        boxState: null,
        isEmpty: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        userdefine1: null,
        userdefine2: null,
        locationId: null,
        isFull: null
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加容器管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBoxInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改容器管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBoxInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBoxInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除容器管理编号为"' + ids + '"的数据项？').then(function() {
        return delBoxInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('box/boxInfo/export', {
        ...this.queryParams
      }, `boxInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
