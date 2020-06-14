/**
 * 笔记本的操作
 * @returns
 */

//根据用户id显示笔记本列表
function loadUserBooks() {
	//获取userId
	var userId=getCookie("userId");
	//判断是否获取到有效的userId
	if(userId==null){
		//转发回登录页面
		window.location.href="login.html";
	}else {//发送ajax请求
		$.ajax({
			url:path+"/book/loadBooks.do",
			type:"post",
			data:{"userId":userId},
			dataType:"json",
			success:function(result){
				//判断查询是否成功
				if(result.status==0){
					//获取笔记本集合
					var books=result.data;
					for(var i=0;i<books.length;i++){
						//获取笔记本ID
						var bookId=books[i].notebook_id;
						//获取笔记本名称
						var bookName=books[i].notebook_name;
						
						//创建一个笔记本列表项的li元素
						createBookLi(bookId,bookName);
					}
				}
			},
			error:function(){
				alert("笔记本加载失败")
			}
		});
	}
};
//创建一个笔记本列表项的li元素
function createBookLi(bookId,bookName){
	var sli="";
	sli+='<li class="online">'
			+
	     '<a>'
			+
	     '<i class="fa fa-book" title="online" rel="tooltip-bottom">'
			+
	     '</i>'
			+
	     bookName
	     	+
	     '<button type="button" class="btn btn-default btn-xs btn_position btn_delete">'
		   	+
		 '<i class="fa fa-times"></i>'
		   	+
		 '</button>'
	     	+
	     '</a>'
	     	+
	     '</li>';
	//将sli字符串转换成jquery对象li元素
	var $li=$(sli);
	//将bookId的值与jquery对象绑定
	$li.data("bookId",bookId);
	//将li元素添加到笔记本ul列表区
	$("#book_ul").append($li);
};

//添加笔记本
function addNoteBook(){
	//获取用户ID
	var userId=getCookie("userId");
	//获取笔记本标题
	var title=$("#input_notebook").val();
	//数据格式检查
	var ok=true;
	if(title==""){
		ok=false;
		$("#title_span").html("笔记本名称非空!");
	}
	if(userId==null){//检查是否生效
		ok=false;
		window.location.href="login.html";
	}
	if(ok){
		//发送ajax请求
		$.ajax({
			url:path+"/notebook/addNoteBook.do",
			type:"post",
			data:{"userId":userId,"title":title},
			dataType:"json",
			success:function(result){
				var book=result.data;
				if(result.status==0){
					var id=book.notebook_id;
					var title=book.notebook_name;
					createBookLi(id,title);//创建一个笔记本列表的li元素
					alert(result.msg);
				}
			},
			error:function(){
				alert("添加笔记本失败");
			}
		});
	}
}; 

//重命名笔记本
function renameNoteBook(){
	//获取用户ID
	var userId=getCookie("userId");
	//获取笔记本ID
	var $li=$("#book_ul a.checked").parent();
	var bookId=$li.data("bookId");
	//获取笔记本名称
	var bookName=$("#input_notebook_rename").val().trim();
	//数据格式检查
	var ok=true;
	if(bookName==""){
		ok=false;
		$("#name_span").html("笔记本名称非空!");
	}
	if(userId==null){//检查是否生效
		ok=false;
		window.location.href="login.html";
	}
	if(ok){
		//发送ajax请求
		$.ajax({
			url:path+"/notebook/renameNoteBook.do",
			type:"post",
			data:{"userId":userId,"bookId":bookId,"bookName":bookName},
			dataType:"json",
			success:function(result){
				var book=result.data;
				if(result.status==0){
					var id=book.notebook_id;
					var noteBookName=book.notebook_name;
					createBookLi(id,noteBookName);//创建一个笔记本列表的li元素
					//移除笔记本li
	 				$li.remove();
					alert(result.msg);
				}
			},
			error:function(){
				alert("重命名笔记本异常!");
			}
		});
	}
};

//加载用户笔记本，生成Option选项
function loadReplaySelect(){
	 //获取用户ID
	 var userId=getCookie("userId");
	 //判断是否获取到有效的userId
	 if(userId==null){
		 //转发回登录页面
		 window.location.href="login.html";
	 }
	 $.ajax({
	     url:path+"/book/loadBooks.do",
	     type:"post",
	     data:{"userId":userId},
	     dataType:"json",
	     success:function(result){
	         if(result.status==0){
	         	var books = result.data;//笔记本json集合信息
	             //循环生成option
	             for(var i=0;i<books.length;i++){
	            	 //获取每个笔记本元素的笔记本ID
	                 var bookId = books[i].notebook_id;
	                 //获取每个笔记本元素的笔记本名称
	                 var bookName = books[i].notebook_name;
	                 //拼一个option
	                 var opts='<option value="'+bookId+'">'
	                 			+
	                 		   bookName
	                 		   	+
	                 		   '</option>';
	                 //将option添加到select中
	                 $("#replaySelect").append(opts);
	              }
	         }
	     }
	 });
};

//彻底删除笔记本
function deleteNoteBook(){
	//获取笔记本ID
	var $li=$("#book_ul a.checked").parent();
	var bookId=$li.data("bookId");
	 //发送Ajax请求
	 $.ajax({
		 url:path+"/notebook/deleteNoteBook.do",
		 type:"post",
		 data:{"bookId":bookId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 //移除li
				 $li.remove();
				 //提示成功
				 alert(result.msg);
			 }
		 },
		 error:function(){
			 alert("彻底删除笔记本异常");
		 }
	 });
};