function test() {
	$.ajax({
		url:"test1",
		data:{
			"str":"你好",
			"str2":"世界"
				},
				type: "POST",
				dataType:"html",
				success: function (data) {
					alert(data)
					}
				})
}