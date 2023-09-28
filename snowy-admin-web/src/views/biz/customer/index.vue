<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="客户名称" name="customerName">
						<a-input v-model:value="searchFormState.customerName" placeholder="请输入客户名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="客户类型" name="customerType">
						<a-select v-model:value="searchFormState.customerType" placeholder="请选择客户类型" :options="customerTypeOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="联系方式" name="customerPhone">
						<a-input v-model:value="searchFormState.customerPhone" placeholder="请输入联系方式" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="地址" name="customerAddress">
						<a-input v-model:value="searchFormState.customerAddress" placeholder="请输入地址" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="客户画像" name="customerProfile">
						<a-input v-model:value="searchFormState.customerProfile" placeholder="请输入客户画像" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tCustomerAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete
						v-if="hasPerm('tCustomerBatchDelete')"
						:selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTCustomer"
					/>
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'customerType'">
					<a-tag v-for="textValue in JSON.parse(record.customerType)" :key="textValue" color="green">{{ $TOOL.dictTypeData('CUSTOMER_TYPE', textValue) }}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tCustomerEdit')">编辑</a>
						<a-divider type="vertical" v-if="hasPerm(['tCustomerEdit', 'tCustomerDelete'], 'and')" />
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTCustomer(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tCustomerDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="customer">
	import tool from '@/utils/tool'
	import Form from './form.vue'
	import tCustomerApi from '@/api/biz/tCustomerApi'
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
			title: '客户名称',
			dataIndex: 'customerName'
		},
		{
			title: '客户类型',
			dataIndex: 'customerType'
		},
		{
			title: '联系方式',
			dataIndex: 'customerPhone'
		},
		{
			title: '地址',
			dataIndex: 'customerAddress'
		},
		{
			title: '客户画像',
			ellipsis: true,
			dataIndex: 'customerProfile'
		},
	]
	// 操作栏通过权限判断是否显示
	if (hasPerm(['tCustomerEdit', 'tCustomerDelete'])) {
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
		return tCustomerApi.tCustomerPage(Object.assign(parameter, searchFormParam)).then((data) => {
			return data
		})
	}
	// 重置
	const reset = () => {
		searchFormRef.value.resetFields()
		table.value.refresh(true)
	}
	// 删除
	const deleteTCustomer = (record) => {
		let params = [
			{
				pkId: record.pkId
			}
		]
		tCustomerApi.tCustomerDelete(params).then(() => {
			table.value.refresh(true)
		})
	}
	// 批量删除
	const deleteBatchTCustomer = (params) => {
		tCustomerApi.tCustomerDelete(params).then(() => {
			table.value.clearRefreshSelected()
		})
	}
	const customerTypeOptions = tool.dictList('CUSTOMER_TYPE')
</script>
