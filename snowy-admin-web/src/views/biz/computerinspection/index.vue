<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="项目名称：" name="inspectionName">
						<a-select showSearch v-model:value="searchFormState.inspectionName" placeholder="请选择项目"
							optionFilterProp="label" :options="projectList" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="巡检类型" name="inspectionType">
						<a-select v-model:value="searchFormState.inspectionType" placeholder="巡检类型"
							:options="typeList" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table ref="table" :columns="columns" :data="loadData" :alert="options.alert.show" bordered
			:row-key="(record) => record.pkId" :tool-config="toolConfig" :row-selection="options.rowSelection">
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tComputerInspectionAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete v-if="hasPerm('tComputerInspectionBatchDelete')" :selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTComputerInspection" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'userList'">
					<a-tag v-for="(user, index) in record.userList" color="cyan" :key="index">{{
						user.name
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'remarkReport'">
					<a-tag v-for="(value, index) in record.remarkReport" color="cyan" :key="index">{{
						value.k + "：" + value.v
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tComputerInspectionEdit')">编辑</a>
						<a-divider type="vertical"
							v-if="hasPerm(['tComputerInspectionEdit', 'tComputerInspectionDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTComputerInspection(record)">
							<a-button type="link" danger size="small"
								v-if="hasPerm('tComputerInspectionDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="computerinspection">
import Form from './form.vue'
import tComputerInspectionApi from '@/api/biz/tComputerInspectionApi'
import tComputerInspectionTypeApi from '@/api/biz/tComputerInspectionTypeApi'
import tProjectApi from '@/api/biz/tProjectApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	{
		title: '项目名称',
		dataIndex: 'projectName'
	},
	{
		title: '巡检人员',
		dataIndex: 'userList'
	},
	// {
	// 	title: '经度',
	// 	dataIndex: 'longitude'
	// },
	// {
	// 	title: '纬度',
	// 	dataIndex: 'latitude'
	// },
	{
		title: '巡检项目',
		dataIndex: 'inspectionTypeName'
	},
	{
		title: '巡检报告',
		dataIndex: 'remarkReport'
	},
	// {
	// 	title: '作业计划',
	// 	dataIndex: 'workPlan'
	// },
	// {
	// 	title: '创建用户',
	// 	dataIndex: 'createdBy'
	// },
	// {
	// 	title: '修改时间',
	// 	dataIndex: 'updatedTime'
	// },
	// {
	// 	title: '修改用户',
	// 	dataIndex: 'updatedBy'
	// },
]
// 操作栏通过权限判断是否显示
if (hasPerm(['tComputerInspectionEdit', 'tComputerInspectionDelete'])) {
	columns.push({
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '150px'
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
	selectProjectList()
	selectTypeList()
	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	return tComputerInspectionApi.tComputerInspectionPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTComputerInspection = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tComputerInspectionApi.tComputerInspectionDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTComputerInspection = (params) => {
	tComputerInspectionApi.tComputerInspectionDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
const projectList = ref([])
const selectProjectList = () => {
	projectList.value = [];
	tProjectApi.projectList().then(res => {
		res.forEach(i => {
			const newI = {
				value: i.pkId,
				label: i.projectName,
			};
			projectList.value.push(newI)
		})
	})
}
const typeList = ref([])
const selectTypeList = () => {
	typeList.value = [];
	tComputerInspectionTypeApi.list().then(res => {
		res.forEach(i => {
			const newI = {
				value: i.pkId,
				label: i.inspectionTypeName,
			};
			typeList.value.push(newI)
		})
	})
}
</script>
