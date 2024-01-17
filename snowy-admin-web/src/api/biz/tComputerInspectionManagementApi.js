import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/computerinspectionmanagement/` + url, ...arg)

/**
 * 机房巡检管理Api接口管理器
 *
 * @author scx
 * @date  2024/01/17 08:46
 **/
export default {
	// 获取机房巡检管理分页
	tComputerInspectionManagementPage(data) {
		return request('page', data, 'get')
	},
	// 获取机房巡检管理列表
	tComputerInspectionManagementList(data) {
		return request('list', data, 'get')
	},
	// 提交机房巡检管理表单 edit为true时为编辑，默认为新增
	tComputerInspectionManagementSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除机房巡检管理
	tComputerInspectionManagementDelete(data) {
		return request('delete', data)
	},
	// 获取机房巡检管理详情
	tComputerInspectionManagementDetail(data) {
		return request('detail', data, 'get')
	}
}
