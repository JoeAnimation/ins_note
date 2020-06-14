//弹出删除笔记确认对话框
function alertDeleteNoteWindow(){
	$("#can").load("./alert/alert_delete_note.html");
	$(".opacity_bg").show();
};
//弹出确认收藏笔记对话框
function alertLikeNoteWindow(){
	$("#can").load("./alert/alert_like.html");
	$(".opacity_bg").show();
};

//弹出删除笔记本确认对话框
function alertDeleteNoteBookWindow(){
	$("#can").load("./alert/alert_delete_notebook.html");
	$(".opacity_bg").show();
};
//弹出新建笔记本对话框
function alertAddBookWindow() {
	//弹出新建笔记本对话框
	$('#can').load('./alert/alert_notebook.html');
	//显示背景
	$('.opacity_bg').show();
};

//关闭对话框,对所有对话框都有效
function closeAlertWindow(){
	//$("#can").empty();
	$("#can").html("");
	$(".opacity_bg").hide();
};
//弹出新建笔记的对话框
function alertAddNoteWindow(){
	//判断是否有笔记本被选中
	var $li=$("#book_ul a.checked").parent();
	if($li.length==0){
		alert("请选择笔记本");
	}else{
		$('#can').load('./alert/alert_note.html');
		$('.opacity_bg').show();
	}
};
//弹出转移笔记对话框
function alertMoveNoteWindow(){
	$(".opacity_bg").show();
	$("#can").load("alert/alert_move.html",function(){
		//为alert_move.html中<select>加载数据
		var books = $("#book_ul li");//获取book列表
		//循环book列表数据
		for(var i=0;i<books.length;i++){
			var $li = $(books[i]);//获取li元素并转为jQuery对象
			var bookId = $li.data("bookId");//获取笔记本id
			var bookName = $li.text().trim();//获取笔记本名
			//创建一个option元素
			var sopt = '';
			sopt+='<option value="'+bookId+'">'
					+
			      bookName
			 		+
			     '</option>';
			//添加到select中
			$("#moveSelect").append(sopt);
		}
	});
};

//弹出重命名笔记本对话框
 function alertRenameBookWindow(){
 	$("#can").load("alert/alert_rename.html");
 	$(".opacity_bg").show();
 };