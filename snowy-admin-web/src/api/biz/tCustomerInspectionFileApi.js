import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/customerinspectionfile/` + url, ...arg)

/**
 * 客户巡检文件Api接口管理器
 *
 * @author scx
 * @date  2023/11/01 10:13
 **/
export default {
	// 获取客户巡检文件分页
	tCustomerInspectionFilePage(data) {
		return request('page', data, 'get')
	},
	// 获取客户巡检文件列表
	tCustomerInspectionFileList(data) {
		return request('list', data, 'get')
	},
	// 提交客户巡检文件表单 edit为true时为编辑，默认为新增
	tCustomerInspectionFileSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除客户巡检文件
	tCustomerInspectionFileDelete(data) {
		return request('delete', data)
	},
	// 获取客户巡检文件详情
	tCustomerInspectionFileDetail(data) {
		return request('detail', data, 'get')
	}
}
