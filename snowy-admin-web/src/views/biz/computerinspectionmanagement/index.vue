<template>
	<a-card :bordered="false">
		<s-table
			ref="table"
			:columns="columns"
			:data="loadData"
			:alert="options.alert.show"
			bordered
			:row-key="(record) => record.pkId"
			:tool-config="toolConfig"
			:row-selection="options.rowSelection"
		>
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tComputerInspectionManagementAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('tComputerInspectionManagementBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTComputerInspectionManagement"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tComputerInspectionManagementEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['tComputerInspectionManagementEdit', 'tComputerInspectionManagementDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTComputerInspectionManagement(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tComputerInspectionManagementDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="computerinspectionmanagement">
	import Form from './form.vue'
	import tComputerInspectionManagementApi from '@/api/biz/tComputerInspectionManagementApi'
	const table = ref()
	const formRef = ref()
	const toolConfig = { refresh: true, height: true, columnSetting: true, striped: false }
	const columns = [
		{
			title: '项目名称',
			dataIndex: 'inspectionName'
		},
		{
			title: '巡检人员',
			dataIndex: 'inspectionUsers'
		},
		{
			title: '经度',
			dataIndex: 'longitude'
		},
		{
			title: '纬度',
			dataIndex: 'latitude'
		},
		{
			title: '巡检项目',
			dataIndex: 'inspectionType'
		},
		{
			title: '巡检报告',
			dataIndex: 'remarkReport'
		},
		{
			title: '作业计划',
			dataIndex: 'workPlan'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['tComputerInspectionManagementEdit', 'tComputerInspectionManagementDelete'])) {
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
		return tComputerInspectionManagementApi.tComputerInspectionManagementPage(parameter).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteTComputerInspectionManagement = (record) => {
		let params = [
			{
				pkId: record.pkId
			}
		]
		tComputerInspectionManagementApi.tComputerInspectionManagementDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchTComputerInspectionManagement = (params) => {
		tComputerInspectionManagementApi.tComputerInspectionManagementDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
