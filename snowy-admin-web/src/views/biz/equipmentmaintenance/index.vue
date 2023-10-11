<template>
	<a-card :bordered="false">
		<a-form ref="searchFormRef" name="advanced_search" :model="searchFormState" class="ant-advanced-search-form">
			<a-row :gutter="24">
				<a-col :span="6">
					<a-form-item label="项目名称" name="idxProjectId">
						<a-input disabled v-model:value="route.query.projectName" placeholder="请在项目管理选择项目" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="设备名称" name="equipmentName">
						<a-input v-model:value="searchFormState.equipmentName" placeholder="请输入设备名称" />
					</a-form-item>
				</a-col>
				<a-col :span="6">
					<a-form-item label="设备类型" name="equipmentType">
						<a-select v-model:value="searchFormState.equipmentType" placeholder="请选择设备类型"
							:options="equipmentTypeOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="设备厂家" name="equipmentManufacturer">
						<a-select v-model:value="searchFormState.equipmentManufacturer" placeholder="请选择设备厂家"
							:options="equipmentManufacturerOptions" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="序列号" name="serialNumber">
						<a-input v-model:value="searchFormState.serialNumber" placeholder="请输入序列号" />
					</a-form-item>
				</a-col>
				<a-col :span="6" v-show="advanced">
					<a-form-item label="授权开始时间" name="authorizationStartTime">
						<a-range-picker v-model:value="searchFormState.authorizationStartTime" show-time />
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
						<component :is="advanced ? 'up-outlined' : 'down-outlined'" />
					</a>
				</a-col>
			</a-row>
		</a-form>
		<s-table ref="table" :columns="columns" :data="loadData" :alert="options.alert.show" bordered
			:row-key="(record) => record.pkId" :tool-config="toolConfig" :row-selection="options.rowSelection">
			<template #operator class="table-operator">
				<a-space>
					<a-button type="primary" @click="formRef.onOpen(route.query.pkId, route.query.projectName)"
						v-if="hasPerm('tEquipmentMaintenanceAdd') && route.query.pkId">
						<template #icon><plus-outlined /></template>
						新增
					</a-button>
					<xn-batch-delete v-if="hasPerm('tEquipmentMaintenanceBatchDelete')" :selectedRowKeys="selectedRowKeys"
						@batchDelete="deleteBatchTEquipmentMaintenance" />
				</a-space>
			</template>
			<template #bodyCell="{ column, record }">
				<template v-if="column.dataIndex === 'authorizationEndTime'">
					<a-tooltip :title="'黄色：即将过期　红色：已经过期'" class="s-tool-item">
						<div :style="'color: ' + record.color">{{ record.authorizationEndTime }}</div>
					</a-tooltip>
				</template>
				<template v-if=" column.dataIndex === 'equipmentType' ">
					{{ $TOOL.dictTypeData('EQUIPMENT_TYPE', record.equipmentType) }}
				</template>
				<template v-if=" column.dataIndex === 'equipmentManufacturer' ">
					{{ $TOOL.dictTypeData('EQUIPMENT_MANUFACTURER', record.equipmentManufacturer) }}
				</template>
				<template v-if=" column.dataIndex === 'equipmentSysUsers' ">
					<a-tag v-for="(  user, index  ) in   record.equipmentSysUserList  " color="cyan" :key=" index ">{{
						user.name
						}}</a-tag>
				</template>
				<template v-if=" column.dataIndex === 'equipmentUsers' ">
					<a-tag v-for="(  user, index  ) in   record.equipmentUserList  " color="cyan" :key=" index ">{{
						user.name
						}}</a-tag>
				</template>
				<template v-if=" column.dataIndex === 'action' ">
					<a-space>
						<a @click="formRef.onOpen(route.query.pkId, route.query.projectName, record)"
							v-if=" hasPerm('tEquipmentMaintenanceEdit') ">编辑</a>
						<a @click="formRef.onOpenTime(route.query.pkId, route.query.projectName, record)"
							v-if=" hasPerm('tEquipmentMaintenanceEdit') ">续费</a>
						<a-popconfirm title="确定要删除吗？" @confirm="deleteTEquipmentMaintenance(record)">
							<a-button type="link" danger size="small"
								v-if=" hasPerm('tEquipmentMaintenanceDelete') ">删除</a-button>
							<a
								@click="$router.push('equipmentmaintenancefile?pkId=' + record.pkId + '&serialNumber=' + record.projectName)">维保文件</a>
						</a-popconfirm>
					</a-space>
				</template>
			</template>
		</s-table>
	</a-card>
	<Form ref="formRef" @successful="table.refresh(true)" />
</template>

<script setup name="equipmentmaintenance">
import tool from '@/utils/tool'
import Form from './form.vue'
import tEquipmentMaintenanceApi from '@/api/biz/tEquipmentMaintenanceApi'
import { useRoute } from 'vue-router'

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
		title: '项目名称',
		dataIndex: 'projectName'
	},
	{
		title: '联系人',
		dataIndex: 'projectContacts'
	},
	{
		title: '联系电话',
		dataIndex: 'projectPhone'
	},
	{
		title: '设备名称',
		dataIndex: 'equipmentName'
	},
	{
		title: '设备类型',
		dataIndex: 'equipmentType'
	},
	{
		title: '设备厂家',
		dataIndex: 'equipmentManufacturer'
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
	{
		title: '设备提醒人',
		dataIndex: 'equipmentUsers'
	}
]
// 操作栏通过权限判断是否显示
if (hasPerm(['tEquipmentMaintenanceEdit', 'tEquipmentMaintenanceDelete'])) {
	columns.push({
		title: '操作',
		dataIndex: 'action',
		align: 'center',
		width: '200px'
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
	searchFormState.idxProjectId = route.query.pkId
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
	return tEquipmentMaintenanceApi.tEquipmentMaintenancePage(Object.assign(parameter, searchFormParam)).then((data) => {
		return data
	})
}
// 重置
const reset = () => {
	searchFormRef.value.resetFields()
	table.value.refresh(true)
}
// 删除
const deleteTEquipmentMaintenance = (record) => {
	let params = [
		{
			pkId: record.pkId
		}
	]
	tEquipmentMaintenanceApi.tEquipmentMaintenanceDelete(params).then(() => {
		table.value.refresh(true)
	})
}
// 批量删除
const deleteBatchTEquipmentMaintenance = (params) => {
	tEquipmentMaintenanceApi.tEquipmentMaintenanceDelete(params).then(() => {
		table.value.clearRefreshSelected()
	})
}
const equipmentTypeOptions = tool.dictList('EQUIPMENT_TYPE')
const equipmentManufacturerOptions = tool.dictList('EQUIPMENT_MANUFACTURER')
// 获取路由传参
const route = useRoute()
</script>
