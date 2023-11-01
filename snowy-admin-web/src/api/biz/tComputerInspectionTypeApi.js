import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/computerinspectiontype/` + url, ...arg)

/**
 * 机房巡检类型Api接口管理器
 *
 * @author scx
 * @date  2023/11/01 10:11
 **/
export default {
	// 获取机房巡检类型分页
	tComputerInspectionTypePage(data) {
		return request('page', data, 'get')
	},
	// 获取机房巡检类型列表
	tComputerInspectionTypeList(data) {
		return request('list', data, 'get')
	},
	// 提交机房巡检类型表单 edit为true时为编辑，默认为新增
	tComputerInspectionTypeSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除机房巡检类型
	tComputerInspectionTypeDelete(data) {
		return request('delete', data)
	},
	// 获取机房巡检类型详情
	tComputerInspectionTypeDetail(data) {
		return request('detail', data, 'get')
	}
}
