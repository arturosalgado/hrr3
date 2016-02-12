$(document).ready(function(){
    $(function()
    {
    	//--
    	//alert('loaded')
        var updateurl = site_url;
       // alert("site only "+site_url)
        //alert(updateurl);
       
        var EDITING = false;
        
        $('.changes').change(function(){
            
            var t =  ($(this).val())
            var update_id  = $(this).attr('update_id');
            var col  = $(this).attr('col');
            
        	var e = $(this);
          
            //alert("update id is "+update_id);
            //alert("col is "+col);
            $.post(updateurl,{value:t,'col':col,'update_id':update_id},function(data){
            	var xml = $(data);
            	  var lrr = xml.find('lrr');
            	  //alert(lrr.text())
            	  $("#LRR-"+update_id).text(lrr.text());
            	  
            	  var x=666;
          	    var currentBackground=$(e).css("background-color");;
          	    
          		$(e).css("background-color","#D9EAD3");
          		setTimeout(function(){
          			  $(e).css("background-color",currentBackground);
          			}, x);
            	  
            	  
             
            }).done(function() {
                //alert( "second success" );
            })
            .fail(function(xhr, textStatus, errorThrown) {
                //alert(xhr.responseText+" e:"+errorThrown);
            })
              .always(function() {
                //alert( "finished" )
                });
            
        });
       
       
        
      
        
    
        
});
    
  // trigger the click from the calendar icon   
  $(function(){
	  
	  $('#mXDP7-btn').click(function(){
		  $('#datepicker1').trigger();
	  });
	  
  });  
    
  $(function() {
      $("#datepicker1").datepicker();
      $("#datepicker2").datepicker();
  });
  
  $(function(){
	  $("#dateForm").submit(function(){
		  var d1 = $('#datepicker1').val();
		  var d2 = $('#datepicker2').val();
		 // alert(d1+" iiii "+d2);
		  if (d1=="" || d2=="")
			  {
			  	alert("Date cannot be empty")
			  	return false;
			  }
	  });
  });
  
  
  
    
    
});




$(document).ready(function(){
	
	$("#calendar-icon-1").click(function(){
		$("#datepicker2").datepicker("show");
	});
	
	$("#calendar-icon-2").click(function(){
		$("#datepicker1").datepicker("show");
	});
	
});

$(document).ready(function(){
	var myFirstColor = undefined;
	$(".changes").focusin(function(){
		myFirstColor = $(this).css("background-color");
		$(this).css("background-color","#F4CCCC");
		this.select();
		
	});
	$(".changes").focusout(function(){
		var e =$(this);
		var bg = $(e).css("background-color");
		
		$(this).css("background-color",myFirstColor);
	});
	
	
	
});
$(document).ready(function(){
$(".keylistener").keypress(function(e){
	
	 if(e.which == 13) {
	        
	        var col =  $(this).attr("col");
            var row = $(this).attr("row");
            var nextRow = parseInt(row);
            //alert("next is "+nextRow)
            //alert("col iis "+col)
            if (nextRow<1000)
            nextRow++;
            var next = '#cell_'+nextRow+'_'+(col);
            //alert(next);
            $(next).focus();
	        
	    }
	
});
});
$(document).ready(function(){
	
	$(".keylistener").focusin(function(){
		
		//$(this).css("border","1px solid red");
		$(this).select();
		
		
	});
	$(".keylistener").focusout(function(){
		
		//$(this).css("border","none");
		
	});
	
});


