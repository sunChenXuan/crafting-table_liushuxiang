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
					<a-form-item label="设备类型" name="fixedAssetType">
						<a-select v-model:value="searchFormState.fixedAssetType" placeholder="请选择设备类型"
							:options="fixedAssetTypeOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="cpu" name="fixedAssetCpu">
						<a-input v-model:value="searchFormState.fixedAssetCpu" placeholder="请输入cpu" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="内存" name="fixedAssetMemory">
						<a-input v-model:value="searchFormState.fixedAssetMemory" placeholder="请输入内存" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="固态硬盘" name="fixedAssetSsd">
						<a-input v-model:value="searchFormState.fixedAssetSsd" placeholder="请输入固态硬盘" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="机械硬盘" name="fixedAssetDisk">
						<a-input v-model:value="searchFormState.fixedAssetDisk" placeholder="请输入机械硬盘" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="显卡" name="fixedAssetGpu">
						<a-input v-model:value="searchFormState.fixedAssetGpu" placeholder="请输入显卡" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="配件" name="fixedAssetAccessory">
						<a-input v-model:value="searchFormState.fixedAssetAccessory" placeholder="请输入配件" />
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
					<a-button type="primary" @click="formRef.onOpen()" v-if="hasPerm('tFixedAssetAdd')">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete v-if="hasPerm('tFixedAssetBatchDelete')" :selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTFixedAsset" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'loaneeUserList'">
					<a-tag v-for="(user, index) in record.loaneeUserList" color="cyan" :key="index">{{
						user.name
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'fixedAssetType'">
					{{ $TOOL.dictTypeData('FIXED_ASSET_TYPE', record.fixedAssetType) }}
				</template>
				<template v-if="column.dataIndex === 'isReturn'">
					<a-tag color="cyan">{{
						record.isReturn == 0 ? "未归还" : "已归还"
					}}</a-tag>
				</template>
				<template v-if="column.dataIndex === 'action'">
					<a-space>
						<a
							@click="$router.push('fixedassetfile?pkId=' + record.pkId + '&serialNumber=' + record.serialNumber)">文件</a>
						<a @click="formRefFlow.onOpen(record)" v-if="record.isReturn == 1">借出</a>
						<a-popconfirm title="确认归还" @confirm="isReturn(record)">
							<a v-if="record.isReturn == 0">归还</a>
						</a-popconfirm>
						<a @click="formRef.onOpen(record)" v-if="hasPerm('tFixedAssetEdit')">修改</a>
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTFixedAsset(record)">
							<a-button type="link" danger size="small" v-if="hasPerm('tFixedAssetDelete')">删除</a-button>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
	<FormFlow ref="formRefFlow" @successful="table.refresh(true)" />
</template>

<script setup name="fixedasset">
import tool from '@/utils/tool'
import Form from './form.vue'
import FormFlow from '../fixedassetflow/form.vue'
import tFixedAssetApi from '@/api/biz/tFixedAssetApi'
let searchFormState = reactive({})
const searchFormRef = ref()
const table = ref()
const formRef = ref()
const formRefFlow = ref()
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
		title: '设备类型',
		dataIndex: 'fixedAssetType'
	},
	{
		title: '是否归还',
		dataIndex: 'isReturn'
	},
	{
		title: '最近借出',
		dataIndex: 'loaneeUserList'
	},
	{
		title: 'cpu',
		dataIndex: 'fixedAssetCpu'
	},
	{
		title: '内存',
		dataIndex: 'fixedAssetMemory'
	},
	{
		title: '固态硬盘',
		dataIndex: 'fixedAssetSsd'
	},
	{
		title: '机械硬盘',
		dataIndex: 'fixedAssetDisk'
	},
	{
		title: '显卡',
		dataIndex: 'fixedAssetGpu'
	},
	{
		title: '配件',
		dataIndex: 'fixedAssetAccessory'
	},
]
// 操作栏通过权限判断是否显示
if (hasPerm(['tFixedAssetEdit', 'tFixedAssetDelete'])) {
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
	return tFixedAssetApi.tFixedAssetPage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTFixedAsset = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tFixedAssetApi.tFixedAssetDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTFixedAsset = (params) => {
	tFixedAssetApi.tFixedAssetDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
const fixedAssetTypeOptions = tool.dictList('FIXED_ASSET_TYPE')
// 归还
const isReturn = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tFixedAssetApi.isReturn(params).then(() => {
		table.value.refresh(true)
	})
}
</script>
