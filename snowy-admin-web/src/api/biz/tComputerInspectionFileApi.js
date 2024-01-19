import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/computerinspectionfile/` + url, ...arg)

/**
 * 机房巡检文件Api接口管理器
 *
 * @author scx
 * @date  2024/01/19 09:56
 **/
export default {
	// 获取机房巡检文件分页
	tComputerInspectionFilePage(data) {
		return request('page', data, 'get')
	},
	// 获取机房巡检文件列表
	tComputerInspectionFileList(data) {
		return request('list', data, 'get')
	},
	// 提交机房巡检文件表单 edit为true时为编辑，默认为新增
	tComputerInspectionFileSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除机房巡检文件
	tComputerInspectionFileDelete(data) {
		return request('delete', data)
	},
	// 获取机房巡检文件详情
	tComputerInspectionFileDetail(data) {
		return request('detail', data, 'get')
	}
}
