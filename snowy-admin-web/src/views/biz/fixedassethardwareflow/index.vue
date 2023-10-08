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

<script setup name="fixedassethardwareflow">
	import tFixedAssetHardwareFlowApi from '@/api/biz/tFixedAssetHardwareFlowApi'
	let searchFormState = reactive({})
	const searchFormRef = ref()
	const table = ref()
	const columns = [
		{
			title: '序列号',
			dataIndex: 'serialNumber'
		},
		{
			title: '备注',
			ellipsis: true,
			dataIndex: 'remark'
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
		return tFixedAssetHardwareFlowApi.tFixedAssetHardwareFlowPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
</script>
