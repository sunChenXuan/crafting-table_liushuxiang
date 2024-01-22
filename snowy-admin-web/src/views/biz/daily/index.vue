<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<!-- <a-form-item label="项目名称" name="objectName">
						<a-input v-model:value="searchFormState.objectName" placeholder="请输入项目名称" />
					</a-form-item> -->
				</a-col>
				<a-col :span="6">
					<a-form-item label="时间" name="time">
						<a-range-picker v-model:value="searchFormState.time" value-format="YYYY-MM-DD HH:mm:ss" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<!-- <a-form-item label="内容" name="remark">
						<a-input v-model:value="searchFormState.remark" placeholder="请输入内容" />
					</a-form-item> -->
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tDailyAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete v-if="hasPerm('tDailyBatchDelete')" :selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTDaily" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tDailyEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['tDailyEdit', 'tDailyDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTDaily(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tDailyDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="daily">
import Form from './form.vue'
import tDailyApi from '@/api/biz/tDailyApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
const columns = [
	// {
	// 	title: '项目名称',
	// 	dataIndex: 'objectName'
	// },
	{
		title: '时间',
		dataIndex: 'time'
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
		title: '内容',
		ellipsis: true,
		dataIndex: 'remark'
	},
	{
		title: '创建用户',
		dataIndex: 'createUserName'
	},
	{
		title: '联系电话',
		dataIndex: 'createUserPhone'
	},
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
if (hasPerm(['tDailyEdit', 'tDailyDelete'])) {
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
	const searchFormParam = JSON.parse(JSON.stringify(searchFormState))
	// time范围查询条件重载
	if (searchFormParam.time) {
		searchFormParam.startTime = searchFormParam.time[0]
		searchFormParam.endTime = searchFormParam.time[1]
		delete searchFormParam.time
	}
	return tDailyApi.tDailyPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTDaily = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tDailyApi.tDailyDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTDaily = (params) => {
	tDailyApi.tDailyDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
</script>
