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
					<a-button type="primary" @click="table.refresh(true)">查询</a-button>
					<a-button style="margin: 0 8px" @click="reset">重置</a-button>
				</a-col>
			</a-row>
		</a-form>
		<s-table
			ref="table"
			:columns="columns"
			:data="loadData"
			bordered
		>
		</s-table>
	</a-card>
</template>

<script setup name="refundflow">
	import tRefundFlowApi from '@/api/biz/tRefundFlowApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const columns = [
		{
			title: '序列号',
			dataIndex: 'serialNumber'
		},
		{
			title: '修改前时间',
			dataIndex: 'authorizationStartTime'
		},
		{
			title: '修改后时间',
			dataIndex: 'authorizationEndTime'
		},
		{
			title: '操作用户',
			dataIndex: 'createUserName'
		},
		{
			title: '操作时间',
			dataIndex: 'createTime'
		},
	]
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
</script>
