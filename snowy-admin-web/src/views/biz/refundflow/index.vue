<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="设备维保id" name="idxEquipmentMaintenanceId">
						<a-input v-model:value="searchFormState.idxEquipmentMaintenanceId" placeholder="请输入设备维保id" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="序列号" name="serialNumber">
						<a-input v-model:value="searchFormState.serialNumber" placeholder="请输入序列号" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="授权开始时间" name="authorizationStartTime">
						<a-range-picker v-model:value="searchFormState.authorizationStartTime" value-format="YYYY-MM-DD HH:mm:ss" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="授权结束时间" name="authorizationEndTime">
						<a-range-picker v-model:value="searchFormState.authorizationEndTime" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
					<a @click="toggleAdvanced" style="margin-left: 8px">
						{{ advanced ? '收起' : '展开' }}
						<component :is="advanced ? 'up-outlined' : 'down-outlined'"/>
					</a>
				</a-col>
			</a-row>
		</a-form>
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tRefundFlowAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('tRefundFlowBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTRefundFlow"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tRefundFlowEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['tRefundFlowEdit', 'tRefundFlowDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTRefundFlow(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tRefundFlowDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="refundflow">
	import Form from './form.vue'
	import tRefundFlowApi from '@/api/biz/tRefundFlowApi'
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
			title: '设备维保id',
			dataIndex: 'idxEquipmentMaintenanceId'
		},
		{
			title: '序列号',
			dataIndex: 'serialNumber'
		},
		{
			title: '授权开始时间',
			dataIndex: 'authorizationStartTime'
		},
		{
			title: '授权结束时间',
			dataIndex: 'authorizationEndTime'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['tRefundFlowEdit', 'tRefundFlowDelete'])) {
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
		// authorizationStartTime范围查询条件重载
		if (searchFormParam.authorizationStartTime) {
			searchFormParam.startAuthorizationStartTime = searchFormParam.authorizationStartTime[0]
			searchFormParam.endAuthorizationStartTime = searchFormParam.authorizationStartTime[1]
			delete searchFormParam.authorizationStartTime
		}
		// authorizationEndTime范围查询条件重载
		if (searchFormParam.authorizationEndTime) {
			searchFormParam.startAuthorizationEndTime = searchFormParam.authorizationEndTime[0]
			searchFormParam.endAuthorizationEndTime = searchFormParam.authorizationEndTime[1]
			delete searchFormParam.authorizationEndTime
		}
		return tRefundFlowApi.tRefundFlowPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteTRefundFlow = (record) => {
		let params = [
			{
				pkId: record.pkId
			}
		]
		tRefundFlowApi.tRefundFlowDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchTRefundFlow = (params) => {
		tRefundFlowApi.tRefundFlowDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
