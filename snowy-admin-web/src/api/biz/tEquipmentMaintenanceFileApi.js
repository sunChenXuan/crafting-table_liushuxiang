import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/equipmentmaintenancefile/` + url, ...arg)

/**
 * 设备维保文件Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取设备维保文件分页
	tEquipmentMaintenanceFilePage(data) {
		return request('page', data, 'get')
	},
	// 获取设备维保文件列表
	tEquipmentMaintenanceFileList(data) {
		return request('list', data, 'get')
	},
	// 提交设备维保文件表单 edit为true时为编辑，默认为新增
	tEquipmentMaintenanceFileSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除设备维保文件
	tEquipmentMaintenanceFileDelete(data) {
		return request('delete', data)
	},
	// 获取设备维保文件详情
	tEquipmentMaintenanceFileDetail(data) {
		return request('detail', data, 'get')
	}
}
