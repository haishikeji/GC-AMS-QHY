<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务编号" prop="taskNo">
        <el-input
          v-model="queryParams.taskNo"
          placeholder="请输入任务编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="起始区域" prop="areaFrom">
        <!--        <el-input-->
        <!--          v-model="queryParams.areaFrom"-->
        <!--          placeholder="请输入起始区域"-->
        <!--          clearable-->
        <!--          size="small"-->
        <!--          @keyup.enter.native="handleQuery"-->
        <!--        />-->
        <el-select v-model="queryParams.areaFrom" @change="locationZoneFromChange" placeholder="请选择起始区域" clearable
                   size="small" style="width: 100%">
          <el-option
            v-for="dict in this.locationZoneCombo"
            :key="dict.zoneId"
            :label="dict.zoneName"
            :value="dict.zoneId"
          />
        </el-select>
      </el-form-item>


      <el-form-item label="起始点位" prop="locationFrom">
        <el-input
          v-model="queryParams.locationFrom"
          placeholder="请输入起始点位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="终点点位" prop="locationTo">
        <el-input
          v-model="queryParams.locationTo"
          placeholder="请输入终点点位"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="终点区域" prop="areaTo">
        <!--        <el-input-->
        <!--          v-model="queryParams.areaTo"-->
        <!--          placeholder="请输入终点区域"-->
        <!--          clearable-->
        <!--          size="small"-->
        <!--          @keyup.enter.native="handleQuery"-->
        <!--        />-->
        <el-select v-model="queryParams.areaTo" @change="locationZoneToChange" placeholder="请选择终点区域" clearable
                   size="small" style="width: 100%">
          <el-option
            v-for="dict in this.locationZoneCombo"
            :key="dict.zoneId"
            :label="dict.zoneName"
            :value="dict.zoneId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="ִ状态" prop="state">
<!--        <el-input-->
<!--          v-model="queryParams.state"-->
<!--          placeholder="请输入ִ状态"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
        <el-select v-model="queryParams.state" clearable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="创建人" prop="createUser">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.createUser"-->
      <!--          placeholder="请输入创建人"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="创建时间" prop="createDate">
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
      <!--      <el-form-item label="仓库" prop="shopId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.shopId"-->
      <!--          placeholder="请输入仓库"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
<!--      <el-form-item label="任务开始时间" prop="startTime">-->
<!--        <el-date-picker clearable size="small"-->
<!--                        v-model="queryParams.startTime"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="选择任务开始时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="任务结束时间" prop="endTime">-->
<!--        <el-date-picker clearable size="small"-->
<!--                        v-model="queryParams.endTime"-->
<!--                        type="date"-->
<!--                        value-format="yyyy-MM-dd"-->
<!--                        placeholder="选择任务结束时间">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
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
          v-hasPermi="['ams:wcsTask:add']"
        >新增</el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleCharge"-->
<!--        >充电任务</el-button>-->
<!--      </el-col>-->
      <!--<el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['ams:wcsTask:edit']"
        >修改</el-button>
      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="danger"-->
      <!--          plain-->
      <!--          icon="el-icon-delete"-->
      <!--          size="mini"-->
      <!--          :disabled="multiple"-->
      <!--          @click="handleCancel()"-->
      <!--          v-hasPermi="['ams:wcsTask:remove']"-->
      <!--        >取消任务</el-button>-->
      <!--      </el-col>-->
      <!--<el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['ams:wcsTask:export']"
        >导出</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wcsTaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务编号" align="center" prop="taskNo"  width="180"/>
      <!--<el-table-column label="仓库" align="center" prop="shopId" />-->
      <!--<el-table-column label="调用系统名称" align="center" prop="sysName" />-->
      <!--<el-table-column label="调用设备名称" align="center" prop="deviceName" />-->
      <el-table-column label="起始区域" align="center" prop="areaFrom" />
      <el-table-column label="起始点位" align="center" prop="locationFrom" />
      <el-table-column label="终点点位" align="center" prop="locationTo" />
      <el-table-column label="AGV站点" align="center" prop="agvStation" />
      <el-table-column label="终点区域" align="center" prop="areaTo" />
      <!--      <el-table-column label="优先级" align="center" prop="priority" />-->
      <el-table-column label="ִ状态" align="center" prop="state" >
        <template slot-scope="scope">
          <div v-if="scope.row.state === 1">开始</div>
          <div v-if="scope.row.state === 2">完成</div>
          <div v-if="scope.row.state === 3">开始取货</div>
          <div v-if="scope.row.state === 4">取货完成</div>
          <div v-if="scope.row.state === 5">开始卸货</div>
          <div v-if="scope.row.state === 6">卸货完成</div>
          <div v-if="scope.row.state === 7">取消</div>
          <div v-if="scope.row.state === 8">异常</div>
          <div v-if="scope.row.state === 9">待下发</div>
          <div v-if="scope.row.state === 10">已下发</div>
          <div v-if="scope.row.state === 11">取消并归于起点</div>
          <div v-if="scope.row.state === 12">取消并归于终点</div>
          <div v-if="scope.row.state === 13">取货待确认</div>
          <div v-if="scope.row.state === 14">卸货待确认</div>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <!--<el-table-column label="创建人" align="center" prop="createUser" />-->
      <el-table-column label="创建时间" align="center" prop="createDate" width="200"/>
      <!--<el-table-column label="任务类型" align="center" prop="taskType" />-->
      <el-table-column label="任务开始时间" align="center" prop="startTime"/>
      <el-table-column label="任务结束时间" align="center" prop="endTime"/>
      <!--<el-table-column label="父任务单号" align="center" prop="parentTask" />-->
      <!--<el-table-column label="AGV接收车号" align="center" prop="agvNo" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.state !== 4 && scope.row.state !== 2 && scope.row.state !== 11 && scope.row.state !== 12"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handlePickupCompleted(scope.row)">取货完成</el-button>
          <el-button
            v-if="(scope.row.state == 4) || (scope.row.state !== 2 && scope.row.state !== 11 && scope.row.state !== 12)"
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleComplete(scope.row)">卸货完成</el-button>
          <el-button
            v-if="scope.row.state !== 7 && scope.row.state !== 2 && scope.row.state !== 11 && scope.row.state !== 12"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleCancel(scope.row)"
            v-hasPermi="['ams:wcsTask:remove']"
          >取消</el-button>
          <!--          <el-button size="mini" type="text" v-if="scope.row.state === 7" @click="handleDisposition(scope.row, 'disFrom')">归于起点</el-button>-->
          <!--          <el-button size="mini" type="text" v-if="scope.row.state === 7" @click="handleDisposition(scope.row, 'disTo')">归于终点</el-button>-->
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

    <!-- 添加或修改AGV任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务类型" prop="businessType">
          <el-select v-model="form.businessType" placeholder="请选择任务类型" clearable size="small" style="width: 100%">
            <el-option
              v-for="dict in businessTypeCombo"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否转发" prop="taskType">
          <el-select v-model="form.taskType" placeholder="请选择" clearable size="small" style="width: 100%">
            <el-option
              v-for="dict in taskTypeCombo"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="起始区域" prop="areaFrom">
          <el-select v-model="form.areaFrom" @change="locationZoneFromChange" placeholder="请选择库区" clearable
                     size="small" style="width: 100%">
            <el-option
              v-for="dict in this.locationZoneCombo"
              :key="dict.zoneId"
              :label="dict.zoneName"
              :value="dict.zoneId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="起始点位" prop="locationFrom">
          <el-select v-model="form.locationFrom" placeholder="请选择库位" clearable size="small" style="width: 100%">
            <el-option
              v-for="dict in locationFromCombo"
              :key="dict.id"
              :label="dict.locationNo"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="目标区域" prop="areaTo">
          <el-select v-model="form.areaTo" @change="locationZoneToChange" placeholder="请选择库区" clearable
                     size="small" style="width: 100%">
            <el-option
              v-for="dict in this.locationZoneCombo"
              :key="dict.zoneId"
              :label="dict.zoneName"
              :value="dict.zoneId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="终点点位" prop="locationTo">
          <el-select v-model="form.locationTo" placeholder="请选择库位" clearable size="small" style="width: 100%">
            <el-option
              v-for="dict in locationToCombo"
              :key="dict.id"
              :label="dict.locationNo"
              :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <!--
        <el-form-item label="优先级" prop="priority">
          <el-input v-model="form.priority" placeholder="请输入优先级" />
        </el-form-item>
        <el-form-item label="起始区域" prop="areaFrom">
          <el-input v-model="form.areaFrom" placeholder="请输入起始区域" />
        </el-form-item>
        <el-form-item label="终点区域" prop="areaTo">
          <el-input v-model="form.areaTo" placeholder="请输入终点区域" />
        </el-form-item>
        <el-form-item label="任务关联单号类型1" prop="whTypeFrom">
          <el-input v-model="form.whTypeFrom" placeholder="请输入任务关联单号类型1" />
        </el-form-item>
        <el-form-item label="任务关联单号1" prop="whNoFrom">
          <el-input v-model="form.whNoFrom" placeholder="请输入任务关联单号1" />
        </el-form-item>
        <el-form-item label="任务关联单号类型2" prop="whTypeTo">
          <el-input v-model="form.whTypeTo" placeholder="请输入任务关联单号类型2" />
        </el-form-item>
        <el-form-item label="任务关联单号2" prop="whNoTo">
          <el-input v-model="form.whNoTo" placeholder="请输入任务关联单号2" />
        </el-form-item>
        <el-form-item label="调用系统名称" prop="sysName">
          <el-input v-model="form.sysName" placeholder="请输入调用系统名称" />
        </el-form-item>
        <el-form-item label="调用设备名称" prop="deviceName">
          <el-input v-model="form.deviceName" placeholder="请输入调用设备名称" />
        </el-form-item>
        <el-form-item label="批号" prop="batchNo">
          <el-input v-model="form.batchNo" placeholder="请输入批号" />
        </el-form-item>
        <el-form-item label="批号2" prop="batchOrderNo">
          <el-input v-model="form.batchOrderNo" placeholder="请输入批号2" />
        </el-form-item>
        <el-form-item label="ִ状态9:(队列等待) 0:(下达AMS成功)" prop="state">
          <el-input v-model="form.state" placeholder="请输入ִ状态9:(队列等待) 0:(下达AMS成功)" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="创建人" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入创建人" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createDate">
          <el-date-picker clearable size="small"
            v-model="form.createDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="编辑人" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入编辑人" />
        </el-form-item>
        <el-form-item label="编辑时间" prop="updateDate">
          <el-date-picker clearable size="small"
            v-model="form.updateDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择编辑时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="仓库" prop="shopId">
          <el-input v-model="form.shopId" placeholder="请输入仓库" />
        </el-form-item>
        <el-form-item label="任务开始时间" prop="startTime">
          <el-date-picker clearable size="small"
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择任务开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="任务结束时间" prop="endTime">
          <el-date-picker clearable size="small"
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择任务结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="父任务单号" prop="parentTask">
          <el-input v-model="form.parentTask" placeholder="请输入父任务单号" />
        </el-form-item>
        <el-form-item label="AGV接收车号" prop="agvNo">
          <el-input v-model="form.agvNo" placeholder="请输入AGV接收车号" />
        </el-form-item>
        <el-form-item label="是否有前置任务，有必须等待前置任务完成" prop="beforeTask">
          <el-input v-model="form.beforeTask" placeholder="请输入是否有前置任务，有必须等待前置任务完成" />
        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!--充电-->
    <el-dialog :title="title" :visible.sync="chaegeOpen" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="车号" prop="agvNo">
          <el-select v-model="form.agvNo" placeholder="请选择车辆" clearable size="small" style="width: 100%">
            <el-option
              v-for="dict in chargeCombo"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitChargeForm">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  listWcsTask,
  getWcsTask,
  delWcsTask,
  addWcsTask,
  updateWcsTask,
  addTestTask,
  completeWcsTask,
  pickupCompleteWcsTask,
  taskCancel,
  invDisposition,
  addChargeTask
} from "@/api/ams/wcsTask";
import { queryLocationDict } from "@/api/base/locationInfo";
import { queryLocationZoneDict } from "@/api/base/locationZone"

export default {
  name: "WcsTask",
  data() {
    return {
      //创建时间范围
      dateRange: [],
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
      // AGV任务表格数据
      wcsTaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 充电弹窗
      chaegeOpen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        locationFrom: null,
        areaFrom: null,
        locationTo: null,
        areaTo: null,
        priority: null,
        state: null,
        createUser: null,
        createDate: null,
        taskType: null,
        shopId: null,
        startTime: null,
        endTime: null,
        parentTask: null,
        agvNo: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      businessTypeCombo: [
        {'label':'普通任务','value':'01'}
      ],
      chargeCombo: [
        {'label':'迷你堆垛A','value':'1'},
        {'label':'迷你堆垛B','value':'2'},
        {'label':'三向车','value':'3'}
      ],
      taskTypeCombo: [
        {'label':'是','value':'Y'},
        {'label':'否','value':'N'}
      ],
      locationFromCombo: [],
      locationToCombo: [],
      locationZoneCombo: [],
     options: [{//状态栏下拉选项
       value: '1',
       label: '开始'
     }, {
       value: '2',
       label: '完成'
     }, {
       value: '3',
       label: '开始取货'
     }, {
       value: '4',
       label: '取货完成'
     }, {
       value: '5',
       label: '开始卸货'
     },{
       value: '6',
       label: '卸货完成'
     },{
       value: '7',
       label: '取消'
     },{
       value: '8',
       label: '异常'
     },{
       value: '9',
       label: '待下发'
     },{
       value: '10',
       label: '已下发'
     },{
       value: '11',
       label: '取消并归于起点'
     },{
       value: '12',
       label: '取消并归于终点'
     },{
       value: '13',
       label: '取货待确认'
     },{
       value: '14',
       label: '卸货待确认'
     }],
    };
  },
  created() {
    this.initData();
    this.getList();
  },
  methods: {
    initData() {
      queryLocationZoneDict().then(response => {
        this.locationZoneCombo = response.data
      })
    },
    /** 查询AGV任务列表 */
    getList() {
      this.loading = true;
      listWcsTask(/*this.queryParams,*/this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.wcsTaskList = response.rows;
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
        taskNo: null,
        businessType: "01",
        whTypeFrom: null,
        whNoFrom: null,
        whTypeTo: null,
        whNoTo: null,
        sysName: null,
        deviceName: null,
        batchNo: null,
        batchOrderNo: null,
        locationFrom: null,
        areaFrom: null,
        locationTo: null,
        areaTo: null,
        priority: null,
        state: null,
        remark: null,
        createUser: null,
        createDate: null,
        updateUser: null,
        updateDate: null,
        taskType: "Y",
        shopId: null,
        startTime: null,
        endTime: null,
        parentTask: null,
        extParam: null,
        ext1: null,
        ext2: null,
        ext3: null,
        ext4: null,
        ext5: null,
        ext6: null,
        ext7: null,
        ext8: null,
        agvNo: null,
        beforeTask: null
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
      this.ids = selection.map(item => item.taskNo)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加AGV任务";
    },
    /** 新增充电任务 */
    handleCharge() {
      this.reset();
      this.chaegeOpen = true;
      this.title = "添加充电任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const taskNo = row.taskNo || this.ids
      getWcsTask(taskNo).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改AGV任务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addTestTask(this.form).then(
            response => {
              this.getList();
              this.$modal.msgSuccess("添加成功");
              this.open = false;
            }
          );
        }
      });
    },
    /** 提交按钮 */
    submitChargeForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addChargeTask(this.form).then(
            response => {
              this.getList();
              this.$modal.msgSuccess("添加成功");
              this.open = false;
            }
          );
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const taskNos = row.taskNo || this.ids;
      this.$modal.confirm('是否确认删除AGV任务编号为"' + taskNos + '"的数据项？').then(function() {
        return delWcsTask(taskNos);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('ams/wcsTask/export', {
        ...this.queryParams
      }, `wcsTask_${new Date().getTime()}.xlsx`)
    },
    handlePickupCompleted (row) {
      const taskNos = row.taskNo
      let vue = this
      this.$modal.confirm('是否确认取货完成"' + taskNos + '"的任务？').then(function() {
        pickupCompleteWcsTask(taskNos).then(response => {
          console.log(response)
          if (response.code == 200) {
            vue.getList();
            vue.$modal.msgSuccess(response.msg);
          } else {
            vue.getList();
            vue.$modal.msgError(response.msg);
          }
        });
      });
    },
    handleComplete (row) {
      const taskNos = row.taskNo
      const vue = this
      this.$modal.confirm('是否确认卸货完成"' + taskNos + '"的任务？').then(function() {
        completeWcsTask(taskNos).then(response => {
          if (response.code === 200) {
            vue.getList();
            vue.$modal.msgSuccess(response.msg);
          } else {
            vue.getList();
            vue.$modal.msgError(response.msg);
          }
        });
      });
    },
    handleCancel (row) {
      const taskNos = row.taskNo
      this.$modal.confirm('是否确认要取消"' + taskNos + '"的任务？').then(function() {
        return taskCancel(taskNos);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("执行成功");
      }).catch(() => {});
    },
    handleDisposition (row, result) {
      const taskNos = row.taskNo
      let resultMsg = ''
      if (result === 'disFrom') {
        resultMsg = '归于起点'
      } else if (result === 'disTo') {
        resultMsg = '归于终点'
      } else {
        resultMsg = '清空库存'
      }
      this.$modal.confirm('确认要进行"' + resultMsg + '"的操作？').then(function() {
        return invDisposition(taskNos, result);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("执行成功");
      }).catch(() => {});
    },
    locationZoneFromChange: function (value) {
      let param = 0
      if (value != '') {
        param = value
      }
      queryLocationDict(param).then(response => {
        this.locationFromCombo = response.data
      })
    },
    locationZoneToChange: function (value) {
      let param = 0
      if (value != '') {
        param = value
      }
      queryLocationDict(param).then(response => {
        this.locationToCombo = response.data
      })
    }
  }
};
</script>
