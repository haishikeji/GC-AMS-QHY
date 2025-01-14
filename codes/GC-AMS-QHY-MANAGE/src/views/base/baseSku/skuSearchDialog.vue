<template>
  <el-dialog
    :title="'物料查询'"
    :close-on-click-modal="false"
    :width="'800px'"
    :modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" label-width="80px">
      <el-row>
        <el-col :span="8">
          <el-form-item label="物料编号" prop="sku">
            <el-input v-model="dataForm.sku" placeholder="物料编号" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="物料名称" prop="skuName">
            <el-input v-model="dataForm.skuName" placeholder="物料名称" clearable></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8" style="text-align: center">
          <el-button type="primary" @click="getDataList()">查询</el-button>
          <el-button type="primary" @click="choseValue1()">选择</el-button>
        </el-col>
      </el-row>
    </el-form>
    <br>
    <div class="el-dialog-div">
      <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
        highlight-current-row
        @current-change="handleCurrentChange"
        @row-dblclick="rowDbClick"
        style="width: 100%;">
        <el-table-column
          prop="sku"
          header-align="center"
          align="center"
          width="200"
          label="物料编码">
        </el-table-column>
        <el-table-column
          prop="desc1"
          header-align="center"
          align="center"
          width="200"
          label="物料名称">
        </el-table-column>
        <el-table-column
          prop="skuType"
          header-align="center"
          align="center"
          label="物料分类">
        </el-table-column>
        <el-table-column
          prop="specs"
          header-align="center"
          align="center"
          width="250"
          label="规格">
        </el-table-column>
        <el-table-column
          prop="model"
          header-align="center"
          align="center"
          label="型号">
        </el-table-column>
        <el-table-column
          prop="packageDesc"
          header-align="center"
          align="center"
          label="单位">
        </el-table-column>
        <el-table-column
          prop="selfCode1"
          header-align="center"
          align="center"
          label="材质">
        </el-table-column>
        <el-table-column
          prop="selfCode2"
          header-align="center"
          align="center"
          label="品牌">
        </el-table-column>
        <el-table-column
          prop="activeFlag"
          header-align="center"
          align="center"
          label="是否启用">
        </el-table-column>
        <el-table-column
          prop="expirationDateCheck"
          header-align="center"
          align="center"
          label="是否定检">
        </el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="dataForm.pageNum"
      :limit.sync="dataForm.pageSize"
      @pagination="getDataList"
    />
  </el-dialog>
</template>

<style>
  .el-dialog-div {
    height: 300px;
    overflow: auto;
  }
</style>

<script>
  import { listBaseSku } from "@/api/base/baseSku";
  export default {
    data () {
      return {
        visible: false,
        roleList: [],
        total: 0,
        dataForm: {
          pageNum: 1,
          pageSize: 10,
          sku: null,
          skuName: null,
          skuType: null,
          choseValue: null,
          choseText: null
        },
        dataList: [],
        dataListLoading: false,
        dataRule: {}
      }
    },
    mounted: function () {
    },
    methods: {
      init (userName, locationNo) {
        this.sku = ''
        this.skuName = ''
        this.skuType = ''
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          this.dataForm.choseValue = userName
          this.dataForm.locationNo = locationNo
          this.dataList = []
          this.getDataList()
        })
      },
      getDataList () {
        this.dataListLoading = true
        listBaseSku(this.dataForm).then(response => {
          this.dataList = response.rows;
          this.total = response.total;
        });
        this.dataListLoading = false
      },
      choseValue1 () {
        this.visible = false
        this.$emit('dochose', this.dataForm.choseText, this.dataForm.choseValue)
      },
      handleCurrentChange (currentRow, oldCurrentRow) {
        if (currentRow) {
          this.dataForm.choseValue = currentRow.sku
          this.dataForm.choseText = currentRow.desc1
        }
      },
      rowDbClick (row, column, event) {
        this.dataForm.choseValue = row.sku
        this.dataForm.choseText = row.desc1
        this.visible = false
        this.$emit('dochose', this.dataForm.choseText, this.dataForm.choseValue)
      }
    }
  }
</script>
