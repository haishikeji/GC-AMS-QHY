<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编号" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="请输入物料编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="物料分类" prop="skuType">
        <el-select v-model="queryParams.skuType" placeholder="请选择物料分类" clearable size="small">
          <el-option
            v-for="dict in skuTypeCombo"
            :key="dict.itemCode"
            :label="dict.itemName"
            :value="dict.itemCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否激活" prop="activeFlag">
        <el-select v-model="queryParams.activeFlag" placeholder="请选择是否激活" clearable size="small">
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
          v-hasPermi="['base:baseSku:add']"
        >新增</el-button>
      </el-col>
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['base:baseSku:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['base:baseSku:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['base:baseSku:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="baseSkuList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--<el-table-column label="货主编号" align="center" prop="customerId" />-->
      <el-table-column label="物料编号" align="center" prop="sku" />
      <el-table-column label="物料类型" align="center" prop="skuType" />
      <el-table-column label="规格" align="center" prop="specs" />
      <el-table-column label="型号" align="center" prop="model" />
      <el-table-column label="物料名称" align="center" prop="desc1" />
      <el-table-column label="描述2" align="center" prop="desc2" />
      <el-table-column label="自赋码1" align="center" prop="selfCode1" />
      <el-table-column label="自赋码2" align="center" prop="selfCode2" />
      <el-table-column label="是否托盘" align="center" prop="hazardFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.hazardFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="包装" align="center" prop="packId" />
      <el-table-column label="皮重" align="center" prop="tare" />
      <el-table-column label="毛重" align="center" prop="grossweight" />
      <el-table-column label="净重" align="center" prop="netweight" />
      <el-table-column label="是否激活" align="center" prop="activeFlag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.activeFlag"/>
        </template>
      </el-table-column>
      <el-table-column label="上架库位" align="center" prop="putawayLocation" />
      <el-table-column label="高" align="center" prop="skuHigh" />
      <el-table-column label="长" align="center" prop="skuLength" />
      <el-table-column label="宽" align="center" prop="skuWidth" />
<!--      <el-table-column label="库存上限" align="center" prop="qtymax" />-->
<!--      <el-table-column label="库存下限" align="center" prop="qtymin" />-->
<!--      <el-table-column label="默认收货单位" align="center" prop="defaultReceivingUom" />-->
<!--      <el-table-column label="默认发货单位" align="center" prop="defaultShipmentUom" />-->
<!--      <el-table-column label="质检规则" align="center" prop="qcRule" />-->
<!--      <el-table-column label="质检周期" align="center" prop="qcTime" />-->
<!--      <el-table-column label="有效期(天)" align="center" prop="shelflife" />-->
<!--      <el-table-column label="冷链标志" align="center" prop="coldFlag">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.coldFlag"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="灭菌标志" align="center" prop="sterilizationFlag">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.sterilizationFlag"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="养护周期(天)" align="center" prop="curingCycle" />-->
<!--      <el-table-column label="生产企业" align="center" prop="manufacturer" />-->
      <!--<el-table-column label="${comment}" align="center" prop="userdefine1" />
      <el-table-column label="${comment}" align="center" prop="userdefine2" />
      <el-table-column label="${comment}" align="center" prop="userdefine3" />
      <el-table-column label="${comment}" align="center" prop="userdefine4" />
      <el-table-column label="${comment}" align="center" prop="userdefine5" />
      <el-table-column label="${comment}" align="center" prop="userdefine6" />
      <el-table-column label="${comment}" align="center" prop="userdefine7" />
      <el-table-column label="${comment}" align="center" prop="userdefine8" />
      <el-table-column label="${comment}" align="center" prop="userdefine9" />
      <el-table-column label="${comment}" align="center" prop="userdefine10" />-->
<!--      <el-table-column label="是否检测效期" align="center" prop="expirationDateCheck">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.expirationDateCheck"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="是否允许超收" align="center" prop="overchargeCheck">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.overchargeCheck"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="是否需要质检" align="center" prop="qcCheck">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.qcCheck"/>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['base:baseSku:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['base:baseSku:remove']"
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

    <!-- 添加或修改物料信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料编号" prop="sku">
              <el-input :disabled="form.customerId != null" v-model="form.sku" placeholder="请输入物料编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料种类" prop="skuType">
              <el-select style="width: 100%" v-model="form.skuType" placeholder="请选择物料种类" clearable size="small">
                <el-option
                  v-for="dict in skuTypeCombo"
                  :key="dict.itemCode"
                  :label="dict.itemName"
                  :value="dict.itemCode"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="物料名称" prop="desc1">
              <el-input v-model="form.desc1" placeholder="请输入物料名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="描述" prop="desc2">
              <el-input v-model="form.desc2" placeholder="描述" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="规格" prop="specs">
              <el-input v-model="form.specs" placeholder="请输入规格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="型号" prop="model">
              <el-input v-model="form.model" placeholder="请输入型号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="自赋码1" prop="selfCode1">
              <el-input v-model="form.selfCode1" placeholder="请输入自赋码1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="自赋码2" prop="selfCode2">
              <el-input v-model="form.selfCode2" placeholder="请输入自赋码2" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="是否托盘" prop="hazardFlag">
              <el-select v-model="form.hazardFlag" placeholder="请选择是否托盘" style="width: 100%">
                <el-option
                  v-for="dict in dict.type.sys_yes_no"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="皮重" prop="tare">
              <el-input v-model="form.tare" placeholder="请输入皮重" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="毛重" prop="grossweight">
              <el-input v-model="form.grossweight" placeholder="请输入毛重" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="净重" prop="netweight">
              <el-input v-model="form.netweight" placeholder="请输入净重" />
            </el-form-item>
          </el-col>
        </el-row>
        <!--<el-row>-->
          <!--<el-col :span="12">-->
            <!--<el-form-item label="长" prop="skuLength">-->
              <!--<el-input v-model="form.skuLength" placeholder="请输入长" />-->
            <!--</el-form-item>-->
          <!--</el-col>-->
          <!--<el-col :span="12">-->
            <!--<el-form-item label="宽" prop="skuWidth">-->
              <!--<el-input v-model="form.skuWidth" placeholder="请输入宽" />-->
            <!--</el-form-item>-->
          <!--</el-col>-->
        <!--</el-row>-->
<!--        <el-row>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="库存上限" prop="qtymax">-->
<!--              <el-input v-model="form.qtymax" placeholder="请输入库存上限" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="库存下限" prop="qtymin">-->
<!--              <el-input v-model="form.qtymin" placeholder="请输入库存下限" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <el-row>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="是否检测效期" prop="expirationDateCheck">-->
<!--              <el-select v-model="form.expirationDateCheck" placeholder="请选择是否检测效期" style="width: 100%">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_yes_no"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="有效期(天)" prop="shelflife">-->
<!--              <el-input v-model="form.shelflife" placeholder="请输入有效期(天)" />-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <el-row>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="是否允许超收" prop="overchargeCheck">-->
<!--              <el-select v-model="form.overchargeCheck" placeholder="请选择是否允许超收" style="width: 100%">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_yes_no"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="是否需要质检" prop="qcCheck">-->
<!--              <el-select v-model="form.qcCheck" placeholder="请选择是否需要质检" style="width: 100%">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_yes_no"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
<!--        <el-row>-->
<!--          <el-col :span="12">-->
<!--            <el-form-item label="是否激活" prop="activeFlag">-->
<!--              <el-select v-model="form.activeFlag" placeholder="请选择是否激活" style="width: 100%">-->
<!--                <el-option-->
<!--                  v-for="dict in dict.type.sys_yes_no"-->
<!--                  :key="dict.value"-->
<!--                  :label="dict.label"-->
<!--                  :value="dict.value"-->
<!--                ></el-option>-->
<!--              </el-select>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--          <el-col :span="12">-->
<!--          </el-col>-->
<!--        </el-row>-->
        <!--<el-row>
          <el-col :span="12">
            <el-form-item label="质检规则" prop="qcRule">
              <el-input v-model="form.qcRule" placeholder="请输入质检规则" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="质检周期" prop="qcTime">
              <el-input v-model="form.qcTime" placeholder="请输入质检周期" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="冷链标志" prop="coldFlag">
          <el-select v-model="form.coldFlag" placeholder="请选择冷链标志">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="灭菌标志" prop="sterilizationFlag">
          <el-select v-model="form.sterilizationFlag" placeholder="请选择灭菌标志">
            <el-option
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="养护周期(天)" prop="curingCycle">
          <el-input v-model="form.curingCycle" placeholder="请输入养护周期(天)" />
        </el-form-item>
        <el-form-item label="生产企业" prop="manufacturer">
          <el-input v-model="form.manufacturer" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBaseSku, getBaseSku, delBaseSku, addBaseSku, updateBaseSku } from "@/api/base/baseSku";
import { querySkuTypeDict } from "@/api/base/skuType";

export default {
  name: "BaseSku",
  dicts: ['sys_yes_no'],
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
      // 物料信息表格数据
      baseSkuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerId: null,
        sku: null,
        skuType: null,
        activeFlag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        sku: [
          { required: true, message: "物料编号不能为空", trigger: "change" }
        ],
        skuType: [
          { required: true, message: "物料分类不能为空", trigger: "change" }
        ],
        activeFlag: [
          { required: true, message: "是否激活不能为空", trigger: "change" }
        ],
        desc1: [
          { required: true, message: "物料名称不能为空", trigger: "change" }
        ]
      },
      skuTypeCombo: []
    };
  },
  created() {
    this.init();
    this.getList();
  },
  methods: {
    init() {
      querySkuTypeDict().then(response => {
        this.skuTypeCombo = response.data;
      });
    },
    /** 查询物料信息列表 */
    getList() {
      this.loading = true;
      listBaseSku(this.queryParams).then(response => {
        this.baseSkuList = response.rows;
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
        customerId: null,
        sku: null,
        skuType: null,
        specs: null,
        model: null,
        desc1: null,
        desc2: null,
        selfCode1: null,
        selfCode2: null,
        hazardFlag: 'N',
        packId: null,
        tare: null,
        grossweight: null,
        netweight: null,
        activeFlag: 'Y',
        putawayLocation: null,
        putawayZone: null,
        putawayRule: null,
        skuHigh: null,
        skuLength: null,
        skuWidth: null,
        qtymax: null,
        qtymin: null,
        defaultReceivingUom: null,
        defaultShipmentUom: null,
        qcRule: null,
        qcTime: null,
        shelflife: null,
        coldFlag: 'N',
        sterilizationFlag: 'N',
        curingCycle: null,
        manufacturer: null,
        userdefine1: null,
        userdefine2: null,
        userdefine3: null,
        userdefine4: null,
        userdefine5: null,
        userdefine6: null,
        userdefine7: null,
        userdefine8: null,
        userdefine9: null,
        userdefine10: null,
        expirationDateCheck: 'N',
        overchargeCheck: 'Y',
        qcCheck: 'N',
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
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
      this.ids = selection.map(item => item.sku)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物料信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const customerId = row.customerId
      const sku = row.sku
      getBaseSku(customerId,sku).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料信息";
      });

    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.customerId != null) {
            updateBaseSku(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBaseSku(this.form).then(response => {
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
      const skus = row.sku || this.ids;
      this.$modal.confirm('是否确认删除物料信息编号为"' + skus + '"的数据项？').then(function() {
        return delBaseSku(skus);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('base/baseSku/export', {
        ...this.queryParams
      }, `baseSku_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
