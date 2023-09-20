import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/computerinspection/` + url, ...arg)

/**
 * 机房巡检Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:03
 **/
export default {
	// 获取机房巡检分页
	tComputerInspectionPage(data) {
		return request('page', data, 'get')
	},
	// 获取机房巡检列表
	tComputerInspectionList(data) {
		return request('list', data, 'get')
	},
	// 提交机房巡检表单 edit为true时为编辑，默认为新增
	tComputerInspectionSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除机房巡检
	tComputerInspectionDelete(data) {
		return request('delete', data)
	},
	// 获取机房巡检详情
	tComputerInspectionDetail(data) {
		return request('detail', data, 'get')
	}
}
