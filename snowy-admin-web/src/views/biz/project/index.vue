<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="项目名称" name="projectName">
						<a-input v-model:value="searchFormState.projectName" placeholder="请输入项目名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="联系人" name="projectContacts">
						<a-input v-model:value="searchFormState.projectContacts" placeholder="请输入联系人" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="联系电话" name="projectPhone">
						<a-input v-model:value="searchFormState.projectPhone" placeholder="请输入联系电话" />
					</a-form-item>
				</a-col>
				<!-- <a-col :span="6" v-show="advanced">
					<a-form-item label="负责人" name="projectHeadUsers">
						<a-select v-model:value="searchFormState.projectHeadUsers" placeholder="请选择负责人" :options="projectHeadUsersOptions" />
					</a-form-item>
				</a-col> -->
				<!-- <a-col :span="6" v-show="advanced">
					<a-form-item label="职工" name="projectUsers">
						<a-select v-model:value="searchFormState.projectUsers" placeholder="请选择职工" :options="projectUsersOptions" />
					</a-form-item>
				</a-col> -->
				<a-col :span="6" v-show="advanced">
					<a-form-item label="项目开始时间" name="projectStartTime">
						<a-range-picker v-model:value="searchFormState.projectStartTime" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="项目结束时间" name="projectEndTime">
						<a-range-picker v-model:value="searchFormState.projectEndTime" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
					<a @click="toggleAdvanced" style="margin-left: 8px">
						{{ advanced ? '收起' : '展开' }}
						<component :is="advanced ? 'up-outlined' : 'down-outlined'" />
					</a>
				</a-col>
			</a-row>
		</a-form>
		<s-table ref="table" :columns="columns" :data="loadData" :alert="options.alert.show" bordered
			:row-key="(record) => record.pkId" :tool-config="toolConfig" :row-selection="options.rowSelection">
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tProjectAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete v-if="hasPerm('tProjectBatchDelete')" :selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTProject" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'projectHeadUsers'">
					<a-tag class="mt-3" v-for="(user, index) in record.projectHeadUserList" color="cyan" :key="index">{{
						user.name
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'projectUsers'">
					<a-tag class="mt-3" v-for="(user, index) in record.projectUserList" color="cyan" :key="index">{{
						user.name
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tProjectEdit')">编辑</a>
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTProject(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tProjectDelete')">删除</a-button>
						</a-popconfirm>
						<a @click="$router.push('projectfile?pkId=' + record.pkId + '&projectName=' + record.projectName)">项目文件</a>
						<a @click="$router.push('equipmentmaintenance?pkId=' + record.pkId + '&projectName=' + record.projectName)">设备维保</a>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="project">
import Form from './form.vue'
import tProjectApi from '@/api/biz/tProjectApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
// 查询区域显示更多控制
const advanced = ref(false)
const toggleAdvanced = () => {
	advanced.value = !advanced.value
}
const columns = [
	{
		title: '项目名称',
		dataIndex: 'projectName'
	},
	{
		title: '项目描述',
		dataIndex: 'projectDescription'
	},
	{
		title: '联系人',
		dataIndex: 'projectContacts'
	},
	{
		title: '联系电话',
		dataIndex: 'projectPhone'
	},
	{
		title: '负责人',
		dataIndex: 'projectHeadUsers'
	},
	{
		title: '职工',
		dataIndex: 'projectUsers'
	},
	{
		title: '项目沟通',
		dataIndex: 'projectCommunication'
	},
	{
		title: '项目风险',
		dataIndex: 'projectRisk'
	},
	{
		title: '采购管理',
		dataIndex: 'purchasingManagement'
	},
	{
		title: '项目开始时间',
		dataIndex: 'projectStartTime'
	},
	{
		title: '项目结束时间',
		dataIndex: 'projectEndTime'
	},
	{
		title: '项目成本',
		dataIndex: 'projectCost'
	},
	{
		title: '项目质量',
		dataIndex: 'projectQuality'
	},
	{
		title: '项目收获',
		dataIndex: 'projectDelivery'
	},
	{
		title: '存在问题',
		dataIndex: 'existingProblems'
	},
]
// 操作栏通过权限判断是否显示
if (hasPerm(['tProjectEdit', 'tProjectDelete'])) {
	columns.push({
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '250px'
	})
}
const selectedRowKeys = ref([])
// 列表选择配置
const options = {
	// columns数字类型字段加入 needTotal: true 可以勾选自动算账
	alert: {
		show: true,
		clear: () => {
			selectedRowKeys.value = ref([])
		}
	},
	rowSelection: {
		onChange: (selectedRowKey, selectedRows) => {
			selectedRowKeys.value = selectedRowKey
		}
	}
}
const loadData = (parameter) => {
	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	// projectStartTime范围查询条件重载
	if (searchFormParam.projectStartTime) {
		searchFormParam.startProjectStartTime = searchFormParam.projectStartTime[0]
		searchFormParam.endProjectStartTime = searchFormParam.projectStartTime[1]
		delete searchFormParam.projectStartTime
	}
	// projectEndTime范围查询条件重载
	if (searchFormParam.projectEndTime) {
		searchFormParam.startProjectEndTime = searchFormParam.projectEndTime[0]
		searchFormParam.endProjectEndTime = searchFormParam.projectEndTime[1]
		delete searchFormParam.projectEndTime
	}
	return tProjectApi.tProjectPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTProject = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tProjectApi.tProjectDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTProject = (params) => {
	tProjectApi.tProjectDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
</script>
