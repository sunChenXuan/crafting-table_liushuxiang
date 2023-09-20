import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/projectfile/` + url, ...arg)

/**
 * 项目文件Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取项目文件分页
	tProjectFilePage(data) {
		return request('page', data, 'get')
	},
	// 获取项目文件列表
	tProjectFileList(data) {
		return request('list', data, 'get')
	},
	// 提交项目文件表单 edit为true时为编辑，默认为新增
	tProjectFileSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除项目文件
	tProjectFileDelete(data) {
		return request('delete', data)
	},
	// 获取项目文件详情
	tProjectFileDetail(data) {
		return request('detail', data, 'get')
	}
}
