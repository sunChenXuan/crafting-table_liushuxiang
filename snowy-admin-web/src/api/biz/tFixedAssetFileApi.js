import { baseRequest } from '@/utils/request'

const request = (url, ...arg) => baseRequest(`/biz/fixedassetfile/` + url, ...arg)

/**
 * 固定资产文件Api接口管理器
 *
 * @author scx
 * @date  2023/09/20 17:02
 **/
export default {
	// 获取固定资产文件分页
	tFixedAssetFilePage(data) {
		return request('page', data, 'get')
	},
	// 获取固定资产文件列表
	tFixedAssetFileList(data) {
		return request('list', data, 'get')
	},
	// 提交固定资产文件表单 edit为true时为编辑，默认为新增
	tFixedAssetFileSubmitForm(data, edit = false) {
		return request(edit ? 'edit' : 'add', data)
	},
	// 删除固定资产文件
	tFixedAssetFileDelete(data) {
		return request('delete', data)
	},
	// 获取固定资产文件详情
	tFixedAssetFileDetail(data) {
		return request('detail', data, 'get')
	}
}
