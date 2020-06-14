function sureSearchShare(event){
	var code=event.keyCode;
	if(code==13){
		//清除原有列表结果
		 $("#pc_part_6 ul").empty();//搜索笔记列表
		 //显示搜索结果列表,其他列表隐藏
		 $("#pc_part_2").hide();//全部笔记
		 $("#pc_part_4").hide();//回收站笔记
		 $("#pc_part_6").show();//搜索笔记列表
		 $("#pc_part_7").hide();//收藏笔记列表
		 $("#pc_part_8").hide();//参加活动的笔记列表
		 $("#pc_part_5").hide();//笔记预览区
		 $("#pc_part_3").show();//笔记编辑区
		//获取请求参数
		keyWord=$("#search_note").val().trim();
		currentPage=1;//要搜索第几页
		//分页加载要分享的笔记
		searchSharePage(keyWord,currentPage);
	}				
};

function moreSearchShare(){
	 //获取请求参数
	 keyWord=$("#search_note").val().trim();
	 //将currentPage加1
	 currentPage=currentPage+1;
	 //发送Ajax请求加载数据
	 searchSharePage(keyWord,currentPage);
 };
 
//弹出收藏分享笔记对话框
 function favorShareNote(){
	 //获取要收藏的分享笔记ID
	 var $li=$(this).parents("li");
	 var shareId=$li.data("shareId");
     //弹出删除对话框
     $(".opacity_bg").show();
     $("#can").load("alert/alert_like_share.html",function(){
         //在html载入完毕后执行
         //将shareId绑定到继续按钮上,当用户点击继续按钮时使用
         $("#sure_favorsharenote").data("shareId",shareId);
        //设置选中效果
     	$("#share_ul a").removeClass("checked");
     	$(this).find("a").addClass("checked");	
     });
 };
 
 //确认收藏分享笔记处理
 function sureFavorShareNote(){
	 //获取请求参数
	 var $li = $(this).parents("li");
	 //获取shareId
	 var shareId = $(this).data("shareId");
	 //发送Ajax请求
	 $.ajax({
		 url:path+"/sharenote/favorShareNote.do",
		 type:"post",
		 data:{"shareId":shareId},
		 dataType:"json",
		 success:function(result){
			 if(result.status==0){
	             //提示删除成功
	             alert(result.msg);
			 }
			 if(result.status==2){
				 alert(result.msg);
			 }
		 },
		 error:function(){
			 alert("确认收藏分享笔记异常");
		 }
	 });
 };
 
 //显示收藏分享笔记列表
 function showFavorShareNotes(){
	//显示回收站列表区
	    $("#pc_part_4").hide();//回收站
	    $("#pc_part_2").hide();//全部笔记
	    $("#pc_part_6").hide();//搜索列表
	    $("#pc_part_7").hide();//收藏列表
	    $("#pc_part_8").show();//参加活动列表
		
	    //清空原有列表元素
	    $("#favor_share_ul").empty();
	    
	    //设置选中效果
		$("#favor_share_ul a").removeClass("checked");
		$(this).find("a").addClass("checked");
	    //获取用户ID
		var userId=getCookie("userId");
		//发送ajax请求
	    $.ajax({
	      url:path+"/sharenote/loadFavorShareNotes.do",
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
	            	  lis+='<li class="offline">'
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
	               //添加到收藏分享笔记列表
	               $("#favor_share_ul").append($li);
	             }
	         }
	       }
	     });
 };
 
 //删除收藏分享笔记
 function deleteFavorShareNote(){
	//获取要删除的收藏分享笔记ID
     var $li = $(this).parents("li");
     var noteId = $li.data("noteId");
     //弹出删除对话框
     $(".opacity_bg").show();
     $("#can").load("alert/alert_delete_share_like.html",function(){
         //在html载入完毕后执行
         //将noteId绑定到继续按钮上,当用户点击继续按钮时使用
         $("#sure_deletefavorsharenote").data("noteId",noteId);
       //设置选中效果
     	$("#favor_share_ul a").removeClass("checked");
     	$(this).find("a").addClass("checked");	
     });
 };
 
 //确认删除收藏分享笔记
 function sureDeleteFavorShareNote(){
	 var $li = $(this).parents("li");
	 //获取noteId
	 var noteId = $(this).data("noteId");
    //发送Ajax请求
    $.ajax({
      url:path+"/sharenote/deleteFavorShareNote.do",
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
	            $("#action_button").click();
         }
      },
      error:function(){
         alert("收藏分享笔记放入回收站异常");
      }
    });
 };
 
 //查看收藏分享笔记信息
 function load_favor_share_note(){
	//获取要查看的回收站笔记ID
	 var noteId = $(this).data("noteId");
	 //发送Ajax请求
	 $.ajax({
		 url:path+"/sharenote/load_favor_share_note.do",
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
			 alert("加载收藏分享笔记信息异常");
		 }
	 });
 };