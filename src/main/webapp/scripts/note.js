/*
 * 笔记的加载
 */
//加载笔记本相关的笔记
function loadBookNotes(){
	$("#pc_part_6").hide();//搜索结果列表区
	$("#pc_part_2").show();//全部笔记列表区
	$("#pc_part_4").hide();//回收站笔记
	$("#pc_part_7").hide();//收藏列表
	$("#pc_part_5").hide();//笔记预览区
	$("#pc_part_3").show();//笔记编辑区
	$("#pc_part_8").hide();//参加活动列表
	//设置选中效果
	$("#book_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");				
	//获取参数
	var bookId=$(this).data("bookId");
	//发送ajax请求
	$.ajax({
		url:path+"/note/loadnotes.do",
		type:"post",
		data:{"bookId":bookId},
		dataType:"json",
		success:function(result){
			//获取笔记信息
			var notes=result.data;//(List集合中存储)
			//清除原来的列表信息
			$("#note_ul").empty();
			//循环添加li
			for(var i=0;i<notes.length;i++){
				//获取笔记ID
				var noteId=notes[i].note_id;
				//获取笔记主题
				var noteTitle=notes[i].note_title;
				//生成笔记li
				createNoteLi(noteId,noteTitle);
			}
		},
		error:function(){
			alert("获取失败");
		}
	});
};

//生成笔记li
function createNoteLi(noteId,noteTitle){
	var sli="";
	sli+='<li class="online">'
				+
	     '<a>'
				+
	     '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
				+
	     noteTitle
	     		+
	     '<dt><button type="button" class="btn btn-default btn-xs btn_position btn_like" title="收藏"><i class="fa fa-star"></i></button></dt>'
	     		+
	     '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>'
	     		+
	     '</a>'
	     		+
	     '<div class="note_menu" tabindex="-1">'
	     		+
	     '<dl>'
	     		+
	     '<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'
	     		+
	     '<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'
	     		+
	     '<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'
	     	    +
	     '</dl>'
	     	    +
	     '</div>'
	     	    +
	     '</li>';
	//将字符串转换为jquery对象
	var $li=$(sli);
	//保存noteId
	$li.data("noteId",noteId);
	//将li添加到ul中
	$("#note_ul").append($li);
}
//笔记信息的标题与内容的显示
function loadNote(){
	//设置选中效果
	$("#note_ul a").removeClass("checked");
	$(this).find("a").addClass("checked");
	//获取请求参数
	var noteId=$(this).data("noteId");
	//发送ajax请求
	$.ajax({
		url:path+"/note/load.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//获取笔记的标题
				var title=result.data.note_title;
				//获取返回的笔记内容
				var body=result.data.note_body;
				//设置页面中笔记标题
				$("#input_note_title").val(title);
				//设置笔记内容
				um.setContent(body);
			}
		},
		error:function(){
			alert("加载笔记信息失败");
		}
	});
};

//更新笔记信息（保存笔记）事件
function updateNote() {
	//获取参数
	var $li=$("#note_ul a.checked").parent();
	//获取笔记Id
	var noteId=$li.data("noteId");
	//获取笔记的标题和内容
	var noteTitle=$("#input_note_title").val().trim();
	var noteBody=um.getContent();
	//发送ajax请求
	$.ajax({
		url:path+"/note/update.do",
		type:"post",
		data:{"noteId":noteId,"noteTitle":noteTitle,"noteBody":noteBody},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//获取笔记的标题
				var noteTitle=result.data.note_title;
				var str="";
				str+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
							+
						noteTitle
				     		+
				     '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down">'
				     		+
				     '<i class="fa fa-chevron-down"></i>'
				     		+
				     '</button>';	
				//将str替换到li的a元素中
				$li.find("a").html(str);
				//提示成功
				alert(result.msg);
			}
		},
		error:function(){
			alert("更新笔记异常");
		}
	});
};

//创建笔记按钮的点击事件
function addNote(){
	//获取请求参数
	//获取笔记标题
	var title=$("#input_note").val().trim();
	//获取用户ID
	var userId=getCookie("userId");
	//获取笔记本ID
	var $li=$("#book_ul a.checked").parent();
	var bookId=$li.data("bookId");
	//数据格式检查
	var ok=true;
	if(title==""){//判断是否为空
		ok=false;
		$("#title_span").html("笔记名称非空!");
	}
	if(userId==null){//检查是否生效
		ok=false;
		window.location.href="login.html";
	}
	if(ok){
		//发送ajax请求
		$.ajax({
			url:path+"/note/add.do",
			type:"post",
			data:{"userId":userId,"bookId":bookId,"title":title},
			dataType:"json",
			success:function(result){
				if(result.status==1){
					alert(result.msg);
				}
				var note=result.data;
				if(result.status==0){
					var id=note.note_id;
					var title=note.note_title;
					createNoteLi(id,title);
					alert(result.msg);
				}
			},
			error:function(){
				alert("新增笔记失败");
			}
		});
	}
};

//分享笔记的点击事件
function shareNotes(){
	//获取请求参数
	var $li=$(this).parents("li");
	var noteId=$li.data("noteId");
	//发送ajax请求
	$.ajax({
		url:path+"/share/add.do",
		type:"post",
		data:{"noteId":noteId},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				var noteTitle=$li.text();
				var sli="";
				sli+='<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
							+
				     noteTitle
				     		+
				    '<i class="fa fa-sitemap"></i>'
				     		+
				    '<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down"><i class="fa fa-chevron-down"></i></button>';	
				//将笔记li元素的<a>标记内容提花
				$li.find("a").html(sli);
				alert(result.msg);
			}
			if(result.status==1){
				alert(result.msg);
			}
		},
		error:function(){
			alert("分享笔记异常!");
		}
	});
};

//分页加载搜索分享的笔记
//发送ajax请求
function searchSharePage(keyWord,currentPage){
	$.ajax({
		url:path+"/share/search.do",
		type:"post",
		data:{"keyWord":keyWord,"currentPage":currentPage},
		dataType:"json",
		success:function(result){
			if(result.status==0){
				//获取服务器返回的搜索结果
				var shares=result.data;
				//循环解析生成列表li元素
				 //循环解析生成列表li元素
				 for(var i=0;i<shares.length;i++){
					 var shareId = shares[i].share_id;//分享ID
					 var shareTitle =shares[i].share_title; //分享标题
					 //生成一个li
					 var sli = "";
					 sli+='<li class="online">'
						 	+
					      '	<a>'
						 	+
					      '		<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
						 	+
					      shareTitle
					      	+
					      '		<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down" title="收藏"><i class="fa fa-star"></i></button>'
					      	+
					      '	</a>'
					      	+
					      '</li>';
					 var $li = $(sli);
				     $li.data("shareId",shareId);
					 //添加到搜索结果ul中
					 $("#pc_part_6 ul").append($li);
				 }
			}
		},
		error:function(){
			alert("搜索失败");
		}
	});
};

//查看搜索结果列表的笔记信息
function load_share(){
	 //获取请求参数
	 var shareId = $(this).data("shareId");
	 //发送Ajax请求
	 $.ajax({
		 url:path+"/note/load_share.do",
		 type:"post",
		 data:{"shareId":shareId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 var title = result.data.share_title;//获取分享标题
				 var body =	result.data.share_body; //获取分享内容
				 //设置标题和内容
				 $("#noput_note_title").html(title);
				 $("#noput_note_title").next().html(body);
				 //切换显示
				 $("#pc_part_3").hide();
				 $("#pc_part_5").show();
			 }
		 },
		 error:function(){
			 alert("加载笔记信息异常");
		 }
	 });
 };
 
 //查看回收站笔记列表笔记信息
 function load_recycle_note(){
	 //获取要查看的回收站笔记ID
	 var noteId = $(this).data("noteId");
	 //发送Ajax请求
	 $.ajax({
		 url:path+"/note/load_recycle_note.do",
		 type:"post",
		 data:{"noteId":noteId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 var title = result.data.note_title;//获取回收站笔记标题
				 var body =	result.data.note_body; //获取回收站笔记内容
				 //设置标题和内容
				 $("#noput_note_title").html(title);
				 $("#noput_note_title").next().html(body);
				 //切换显示
				 $("#pc_part_3").hide();
				 $("#pc_part_5").show();
			 }
		 },
		 error:function(){
			 alert("加载回收站笔记信息异常");
		 }
	 });
 };
 
 //查看收藏笔记列表笔记信息
 function load_favor_note(){
	 //获取要查看的回收站笔记ID
	 var noteId = $(this).data("noteId");
	 //发送Ajax请求
	 $.ajax({
		 url:path+"/note/load_favor_note.do",
		 type:"post",
		 data:{"noteId":noteId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
				 var title = result.data.note_title;//获取回收站笔记标题
				 var body =	result.data.note_body; //获取回收站笔记内容
				 //设置标题和内容
				 $("#noput_note_title").html(title);
				 $("#noput_note_title").next().html(body);
				 //切换显示
				 $("#pc_part_5").show();//笔记预览区
				 $("#pc_part_3").hide();//笔记编辑区
			 }
		 },
		 error:function(){
			 alert("加载收藏笔记信息异常");
		 }
	 });
 };
 
//转移笔记
function moveNote(){
 	//获取请求参数
 	//获取要转移的笔记ID
 	var $li = $("#note_ul a.checked").parent();
 	var noteId = $li.data("noteId");
 	//获取要转入的笔记本ID
 	var bookId = $("#moveSelect").val();
 	//发送Ajax请求
 	$.ajax({
 		url:path+"/note/move.do",
 		type:"post",
 		data:{"noteId":noteId,"bookId":bookId},
 		dataType:"json",
 		success:function(result){
 			if(result.status==0){
 				//移除笔记li
 				$li.remove();
 				//提示成功
 				alert(result.msg);
 			}
 		},
 		error:function(){
 			alert("转移笔记异常!");
 		}
 	});
 };
 
//确认将笔记移入回收站
function recycleNote(){
    //获取笔记ID
    var $li = $("#note_ul a.checked").parent();
    var noteId = $li.data("noteId");
    //发送Ajax请求
    $.ajax({
      url:path+"/note/recycle.do",
      type:"post",
      data:{"noteId":noteId},
      dataType:"json",
      success:function(result){
         if(result.status==0){
             //删除笔记li
             $li.remove();
             //清空笔记编辑区
             $("#input_note_title").val("");
             um.setContent("");
             //提示删除成功
             alert(result.msg);
         }
      },
      error:function(){
         alert("笔记放入回收站异常");
      }
    });
};

//显示回收站笔记信息
function showRecycleNotes(){
    //显示回收站列表区
    $("#pc_part_4").show();//回收站
    $("#pc_part_2").hide();//全部笔记
    $("#pc_part_6").hide();//搜索列表
    $("#pc_part_7").hide();//收藏列表
    $("#pc_part_8").hide();//参加活动列表
    //清空原有列表元素
    $("#recycle_ul").empty();
    //获取用户ID
	var userId=getCookie("userId");
    //发送ajax请求
    $.ajax({
      url:path+"/note/loadDeleteNotes.do",
      type:"post",
      data:{"userId":userId},
      dataType:"json",
      success:function(result){
         if(result.status==0){
            var notes = result.data;
            //循环生成回收站列表
            for(var i=0;i<notes.length;i++){
               var noteId = notes[i].note_id;
               var noteTitle = notes[i].note_title;
               //拼一个li
               var lis=""
               lis+='<li class="disable">'
            		   	+
            		 '<a >'
            		   	+
            		 '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
            		   	+
            		   noteTitle
            		   	+
            		 '<button type="button" class="btn btn-default btn-xs btn_position btn_delete">'
            		   	+
            		 '<i class="fa fa-times"></i>'
            		   	+
            		 '</button>'
            		   	+
            		 '<button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay">'
            		   	+
            		 '<i class="fa fa-reply"></i></button></a></li>';
               var $li = $(lis);
               $li.data("noteId",noteId);
               //添加到回收站列表
               $("#recycle_ul").append($li);
             }
         }
       }
     });
 };
 
//恢复笔记
function replayNote(){
     //获取要恢复的笔记的ID
     var $li = $(this).parents("li");
     var noteId = $li.data("noteId");
     //弹出恢复对话框
     $(".opacity_bg").show();
     $("#can").load("alert/alert_replay.html",function(){
         //在html载入完毕后执行
         //将noteId绑定到恢复按钮上,当用户点击恢复按钮时使用
         $("#sure_replay").data("noteId",noteId);
         //加载笔记本信息,生成下拉单选择项
     	loadReplaySelect();
     });
 };
 
 //确认恢复笔记处理
function sureReplay(){
    //获取noteId
    var noteId = $(this).data("noteId");
    //获取笔记本id
    var bookId = $("#replaySelect").val();
   //发送Ajax请求
   $.ajax({
      url:path+"/note/replay.do",
      type:"post",
      data:{"noteId":noteId,"bookId":bookId},
      dataType:"json",
      success:function(result){
         if(result.status==0){
            //关闭对话框
        	 closeAlertWindow();
            //刷新回收站列表显示
            $("#rollback_button").click();
           }
         },
         error:function(){
        	 alert("恢复笔记异常");
         }
     });
 };
 
 //删除笔记
function deleteNote(){
	 //获取要彻底删除的笔记的ID
     var $li = $(this).parents("li");
     var noteId = $li.data("noteId");
     //弹出恢复对话框
     $(".opacity_bg").show();
     $("#can").load("alert/alert_delete_rollback.html",function(){
         //在html载入完毕后执行
         //将noteId绑定到继续按钮上,当用户点击继续按钮时使用
         $("#sure_deletenote").data("noteId",noteId);
       //设置选中效果
     	$("#recycle_ul a").removeClass("checked");
     	$(this).find("a").addClass("checked");	
     });
 };
 
 //确认彻底删除笔记处理
 function deleteRecycleNote(){
	 var $li = $(this).parents("li");
	 //获取noteId
	 var noteId = $(this).data("noteId");
 	 //发送Ajax请求
 	 $.ajax({
 		 url:path+"/note/deleteRecycleNote.do",
 		 type:"post",
 		 data:{"noteId":noteId},
 		 dataType:"json",
 		 success:function(result){
 			 if(result.status==0){
 				 //移除li
 				 $li.remove();
 				 //提示成功
 				 alert(result.msg);
 				//刷新回收站列表显示
 	            $("#rollback_button").click();
 			 }
 		 },
 		 error:function(){
 			 alert("彻底删除笔记异常");
 		 }
 	 });
 };
 
 //确认收藏笔记处理
 function sureFavorNote(){
	    //获取笔记ID
	    var $li = $("#note_ul a.checked").parent();
	    var noteId = $li.data("noteId");
	    //发送Ajax请求
	    $.ajax({
	      url:path+"/note/favorNote.do",
	      type:"post",
	      data:{"noteId":noteId},
	      dataType:"json",
	      success:function(result){
	         if(result.status==0){
	             //删除笔记li
	             $li.remove();
	             //清空笔记编辑区
	             $("#input_note_title").val("");
	             um.setContent("");
	             //提示删除成功
	             alert(result.msg);
	         }
	      },
	      error:function(){
	         alert("收藏笔记异常");
	      }
	    });
 };
 
 //显示收藏笔记列表
 function showFavorNotes(){
	 	//显示回收站列表区
	    $("#pc_part_4").hide();//回收站
	    $("#pc_part_2").hide();//全部笔记
	    $("#pc_part_6").hide();//搜索列表
	    $("#pc_part_7").show();//收藏列表
	    $("#pc_part_8").hide();//参加活动列表
		
	    //清空原有列表元素
	    $("#favor_ul").empty();
	    
	    //设置选中效果
		$("#favor_ul a").removeClass("checked");
		$(this).find("a").addClass("checked");
	    //获取用户ID
		var userId=getCookie("userId");
	    //发送ajax请求
	    $.ajax({
	      url:path+"/note/loadFavorNotes.do",
	      type:"post",
	      data:{"userId":userId},
	      dataType:"json",
	      success:function(result){
	         if(result.status==0){
	            var notes = result.data;
	            //循环生成回收站列表
	            for(var i=0;i<notes.length;i++){
	               var noteId = notes[i].note_id;
	               var noteTitle = notes[i].note_title;
	               //拼一个li
	               var lis=""
	               lis+='<li class="idle">'
	            	   		+
	            	   '<a >'
	            	   		+
	            	   '<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'
	            	   		+
	            	   	noteTitle
	            	   		+
	            	   '<button type="button" class="btn btn-default btn-xs btn_position btn_delete" title="取消收藏"><i class="fa fa-times"></i></button>'
	            	   		+
	            	   	'</a>'
	            	   		+
	            	   	'</li>';
	               var $li = $(lis);
	               $li.data("noteId",noteId);
	               //添加到回收站列表
	               $("#favor_ul").append($li);
	             }
	         }
	       }
	     });
 };
 
 //删除收藏笔记
 function deleteFavorNote(){
 	  //获取要删除的收藏笔记ID
      var $li = $(this).parents("li");
      var noteId = $li.data("noteId");
      //弹出删除对话框
      $(".opacity_bg").show();
      $("#can").load("alert/alert_delete_like.html",function(){
          //在html载入完毕后执行
          //将noteId绑定到继续按钮上,当用户点击继续按钮时使用
          $("#sure_deletefavornote").data("noteId",noteId);
        //设置选中效果
      	$("#favor_ul a").removeClass("checked");
      	$(this).find("a").addClass("checked");	
      });
  };
  
 //确认删除收藏笔记操作
 function sureDeleteFavorNote(){
	 var $li = $(this).parents("li");
	 //获取noteId
	 var noteId = $(this).data("noteId");
    //发送Ajax请求
    $.ajax({
      url:path+"/note/deleteFavorNote.do",
      type:"post",
      data:{"noteId":noteId},
      dataType:"json",
      success:function(result){
         if(result.status==0){
	        	 //删除笔记li
	             $li.remove();
	             //清空笔记编辑区
	             $("#input_note_title").val("");
	             um.setContent("");
	             //提示删除成功
	             alert(result.msg);
				//刷新收藏笔记列表显示
	            $("#like_button").click();
         }
      },
      error:function(){
         alert("收藏笔记放入回收站异常");
      }
    });
 };