<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
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
			bordered
		>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'loaneeUserList'">
					<a-tag v-for="(user, index) in record.loaneeUserList" color="cyan" :key="index">{{
						user.name
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'isReturn'">
					<a-tag color="cyan">{{
						record.isReturn == 0 ? "未归还" : "已归还"
					}}</a-tag>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="fixedassetflow">
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
			title: '序列号',
			dataIndex: 'serialNumber'
		},
		{
			title: '是否归还',
			dataIndex: 'isReturn'
		},
		{
			title: '借出人',
			dataIndex: 'loaneeUserList'
		},
		{
			title: '备注',
			ellipsis: true,
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
	]
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
