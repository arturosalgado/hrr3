var updateurl = site_url;

$(document).ready(function(){
	
	$("#calendar-icon-1").click(function(){
		$("#datepicker2").datepicker("show");
	});
	
	$("#calendar-icon-2").click(function(){
		$("#datepicker1").datepicker("show");
	});
	
});

$(document).ready(function(){
   
	
	$(".keylistener").focus(function(){
		
		$(this).select();
		
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
		  	alert("Date cannot be empty.")
				  	return false;
				  }
		  });
	  });
	$('.changes').change(function(){
           //--
           var t =  ($(this).val())
           var update_id  = $(this).attr('update_id');
           var col  = $(this).attr('col');
           ///
       	var e = $(this);
         
           //alert("update id is "+update_id);
           //alert("col is "+col);
           $.post(updateurl,{value:t,'col':col,'update_id':update_id},function(data){
           	var xml = $(data);
           	  var lrr = xml.find('lrr');
           	  //alert(lrr.text())
           	  //if (lrr.text()!="")
           	  
           	  if (col<12 && col >=1)
           	  {
           		  $("#LRR-"+update_id).text(lrr.text());
           	  }
           	  var x=400;
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
	
    $('.keylistener').keyup(function(e){
        var col =  $(this).attr("col");
        var row = $(this).attr("row");
        var current_object = $(this);
        //console.log("col"+col);
        //console.log("row"+row);
        //alert($(this).val())
        switch(e.which)
        {
            case 39://right
            	//alert("right")
                var nextCol = parseInt(col);
                if (nextCol<19)
                nextCol++;
                var next = '#cell_'+row+'_'+(nextCol);
               // alert(next);
                $(next).focus();
            break;
            case 13:// enter
            	 
//            	 if (EDITING==false){
//            		 EDITING  = true;
//            		 console.log("Editing set to true"); 
//            	 }	 
//            	 else{
//            		 EDITING = false;
//            		 console.log("Editing set false");
//            	 }
//            	 if (EDITING==true)
//            	 $(this).off('keydown');
//            	 else
//            	 $(this).on('keydown');	 
            	 
            	 break;
             case 40://down
                var nextRow = parseInt(row);
                //alert("next is "+nextRow)
                //alert("col iis "+col)
                if (nextRow<1000)
                nextRow++;
                var next = '#cell_'+nextRow+'_'+(col);
                //alert(next);
                $(next).focus();
            break;
             case 38://up
                var nextRow = parseInt(row);
                if (nextRow>0)
                nextRow--;
                var next = '#cell_'+nextRow+'_'+(col);
                //alert(next);
                $(next).focus();
            break;
             case 37://left
            	 
                var nextCol = parseInt(col);
                //alert('left '+nextCol)
                if(nextCol>=0)
                nextCol--;
                var next = '#cell_'+row+'_'+(nextCol);
                //alert(next);
                $(next).focus();
            break;
            
        }
       
    });
    
});


