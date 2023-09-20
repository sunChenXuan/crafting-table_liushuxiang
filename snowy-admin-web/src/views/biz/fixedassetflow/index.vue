<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="固定资产id" name="idxFixedAssetId">
						<a-input v-model:value="searchFormState.idxFixedAssetId" placeholder="请输入固定资产id" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="序列号" name="serialNumber">
						<a-input v-model:value="searchFormState.serialNumber" placeholder="请输入序列号" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="开始时间" name="startTime">
						<a-range-picker v-model:value="searchFormState.startTime" value-format="YYYY-MM-DD HH:mm:ss" show-time />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="结束时间" name="endTime">
						<a-range-picker v-model:value="searchFormState.endTime" show-time />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tFixedAssetFlowAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('tFixedAssetFlowBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTFixedAssetFlow"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'isReturn'">
					{{ $TOOL.dictTypeData('GENDER', record.isReturn) }}
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tFixedAssetFlowEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['tFixedAssetFlowEdit', 'tFixedAssetFlowDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTFixedAssetFlow(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tFixedAssetFlowDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="fixedassetflow">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import tFixedAssetFlowApi from '@/api/biz/tFixedAssetFlowApi'
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
			title: '固定资产id',
			dataIndex: 'idxFixedAssetId'
		},
		{
			title: '序列号',
			dataIndex: 'serialNumber'
		},
		{
			title: '备注',
			dataIndex: 'remark'
		},
		{
			title: '开始时间',
			dataIndex: 'startTime'
		},
		{
			title: '结束时间',
			dataIndex: 'endTime'
		},
		{
			title: '是否归还',
			dataIndex: 'isReturn'
		},
		{
			title: '创建用户',
			dataIndex: 'createdBy'
		},
		{
			title: '修改时间',
			dataIndex: 'updatedTime'
		},
		{
			title: '修改用户',
			dataIndex: 'updatedBy'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['tFixedAssetFlowEdit', 'tFixedAssetFlowDelete'])) {
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
		// startTime范围查询条件重载
		if (searchFormParam.startTime) {
			searchFormParam.startStartTime = searchFormParam.startTime[0]
			searchFormParam.endStartTime = searchFormParam.startTime[1]
			delete searchFormParam.startTime
		}
		// endTime范围查询条件重载
		if (searchFormParam.endTime) {
			searchFormParam.startEndTime = searchFormParam.endTime[0]
			searchFormParam.endEndTime = searchFormParam.endTime[1]
			delete searchFormParam.endTime
		}
		return tFixedAssetFlowApi.tFixedAssetFlowPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteTFixedAssetFlow = (record) => {
		let params = [
			{
				pkId: record.pkId
			}
		]
		tFixedAssetFlowApi.tFixedAssetFlowDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchTFixedAssetFlow = (params) => {
		tFixedAssetFlowApi.tFixedAssetFlowDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
</script>
