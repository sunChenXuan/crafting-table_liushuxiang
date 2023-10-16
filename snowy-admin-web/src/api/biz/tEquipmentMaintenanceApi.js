import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/equipmentmaintenance/` + url, ...arg)

/**
 * 设备维保Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取设备维保分页
	tEquipmentMaintenancePage(data) {
		return request('page', data, 'get')
	},
	// 获取设备维保列表
	tEquipmentMaintenanceList(data) {
		return request('list', data, 'get')
	},
	// 提交设备维保表单 edit为true时为编辑，默认为新增
	tEquipmentMaintenanceSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 带文件提交
	addOrFile(data) {
		return request('addOrFile', data)
	},
	// 删除设备维保
	tEquipmentMaintenanceDelete(data) {
		return request('delete', data)
	},
	// 获取设备维保详情
	tEquipmentMaintenanceDetail(data) {
		return request('detail', data, 'get')
	},
	// 获取设备维保详情
	projectList() {
		return request('projectList', null, 'get')
	}
}
