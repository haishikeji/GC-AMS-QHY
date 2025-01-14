<template>
  <el-dialog :title="title" :visible.sync="adjDialogVisible" width="600px" append-to-body>
    <el-form ref="form" :model="form" :rules="rules" label-width="80px" :inline="true">
      <el-form-item label="库位编码" prop="locationNo">
        <el-input v-model="form.locationNo" placeholder="请输入库位编码"/>
      </el-form-item>
      <el-form-item label="货主" prop="customerId">
        <el-input v-model="form.customerId" placeholder="请输入货主"/>
      </el-form-item>
      <el-form-item label="库存件数" prop="qty">
        <el-input v-model="form.qty" placeholder="请输入库存件数"/>
      </el-form-item>
      <el-form-item label="产品代码" prop="sku">
        <el-input v-model="form.sku" placeholder="请输入产品代码"/>
      </el-form-item>
      <el-form-item label="调整件数" prop="qtyTo">
        <el-input v-model="form.qtyTo" placeholder="请输入调整件数"/>
      </el-form-item>
      <el-form-item label="原因描述" prop="reason">
        <el-input v-model="form.reason" placeholder="请输入原因描述"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>

import {adjInvLotLocId} from "@/api/ams/invLotLocId";

export default {
  name: "adjDialog",
  data() {
    return {
      adjDialogVisible: false,
      title: "库存调整",
      tableSelection: [],
      form: {
        lotnum: "",
        locationNo: "",
        customerId: "",
        sku: "",
        qty: "",
        qtyTo: "",
        reason: ""
      },
      rules: {
        qtyTo: [
          {required: true, message: "请输入调整件数", trigger: "blur"}
        ],
        reason: [
          {required: true, message: "请输入调整原因", trigger: "blur"}
        ],
      }

    };
  },
  methods: {
    show(tableSelection) {
      this.tableSelection = tableSelection;
      this.form = this.tableSelection[0];
      this.adjDialogVisible = true;
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.$modal.loading();
          adjInvLotLocId(this.form).then(response => {
            if (response.code == 200) {
              this.$modal.msgSuccess(response.msg);
              this.adjDialogVisible = false;
              this.$emit('getList');
            } else {
              this.$modal.msgError(response.msg);
            }
          }).catch(error => {
          }).finally(() => {
            this.$modal.closeLoading();
          });
        }
      });
    },
    cancel() {
      this.adjDialogVisible = false;
    }
  }
};
</script>
