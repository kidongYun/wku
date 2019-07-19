(function($) {

	function fe_gnb(){
		var $gnb = $("#gnb");
		var $trigger = $gnb.find(">ul>li");
		var $submenu = $trigger.find(">.sub-menu");
		var $submenuItem = $("#gnb>ul>li>.sub-menu>.inner>ul>li");
		var desktopAgrnt = "win16|win32|win64|mac";
 		var isDesktop = desktopAgrnt.indexOf(navigator.platform.toLowerCase()) > -1;

		$trigger.each(function(i){ i = i+1; $(this).addClass("nav"+i); });

		$trigger.find(">a").on("mouseenter focus", function(){
			if ( $(window).width() > 767 ){
				$submenu.show();
				$submenu.hide();
				if ( isDesktop ){
					$(this).parent().find(">.sub-menu").slideDown(300);
				} else {
					$(this).parent().find(">.sub-menu").show();
				}
			}
		});

		$trigger.find(">a").on("click", function(){
			if ( $(window).width() < 768 ){
				$submenu.removeAttr("style");
				$trigger.removeClass("on").addClass("off");
				$(this).parent().removeClass("off").addClass("on");
				$(this).parent().find(".sub-menu").show();
				heightResize();
				return false;
			}
		});

		$gnb.on("mouseleave", function(){
			if ( $(window).width() > 767 ){
				$submenu.hide();
			}
		});

		var $gnbBtn = $("#header .gnbBtn");
		var $utilList = $("#header .util ul");

		$gnbBtn.on("click", function(){
			if ( !$gnb.find("button").length ){
				$gnb.append('<button type="button">�リ린</button>');
			}
			if ( $(".topVisual .headline").length ) $trigger.eq(0).addClass("current").siblings().removeClass("current");
			heightResize();
		});

		$gnb.on("click", "button", function(){
			$gnb.animate({ "right":"-100%" },300 );
			$trigger.removeClass("on").removeClass("off");
			$("#wrap, #gnb").css("height","");
			$utilList.hide();
		});

		function heightResize(){
			$("#wrap, #gnb").css("height","");
			var gnbHeight = $gnb.outerHeight();
			var submenuHeight = $trigger.find(">.sub-menu:visible").outerHeight();

			if ( gnbHeight < submenuHeight ) $gnb.height( submenuHeight-100 );
			$("#wrap").css("height",$gnb.outerHeight());
			$gnb.animate({ "right":0 },300, function(){
				$utilList.fadeIn(300);
				$utilList.css("top",$("#gnb>ul").outerHeight()+70);
			});
		}

		$(window).resize(function(){
			if ( $(window).width() < 768 ){
				$submenu.removeAttr("style");
			} else {
				$("#wrap, #gnb").removeAttr("style");
				$utilList.removeAttr("style");
			}
			$submenu.removeAttr("style");
		});
	}

	function fe_utilNav(){
		$trigger = $("#header .util	ul li");

		$trigger.find("a").on("mouseenter focus click", function(){
			if ( $(window).width() > 767 ){
				if ( $(this).parent().hasClass("menu-item-has-children") && $(this).parent().find("ul").is(":hidden") ){
					$(this).parent().addClass("current");
					$(this).parent().find("ul").stop().slideDown(300);
					return false;
				} else if ( $(this).parent().hasClass("menu-item-has-children") && $(this).parent().find("ul").is(":visible") ){
					$(this).parent().find("ul").stop().slideUp(300);
					return false;
				}
			}
		});

		if ( $(window).width() > 767 ){
			$trigger.on("mouseleave", function(){
				$(this).parent().find("ul").stop().slideUp(300);
			});
		}
	}

	function fe_searchBox(){
		var $trigger = $(".searchBtn");
		var $searchForm = $("#header .util form");
		var $closeBtn = $(".searchClose");

		$trigger.on("click", function(){
			if ( $searchForm.is(":hidden") ){
				$searchForm.slideDown(300);
			} else {
				$searchForm.slideUp(300);
			}
		});

		$closeBtn.on("click", function(){
			$searchForm.slideUp(300);
		});

		$(window).resize(function(){
			if ( $(window).width() > 960 ){
				$searchForm.removeAttr("style");
			}
		});
	}

	function fe_lnb(){
		var $lnb = $("#lnb");
		var $trigger = $lnb.find("ul>li");
		var $submenu = $trigger.find(">.sub-menu");

		$submenu.hide();
		$trigger.each(function(){
			if ( $(this).hasClass("menu-item-has-children") && !$(this).hasClass("hide") ){
				if ( $(this).hasClass("current-menu-item") || $(this).hasClass("current-menu-ancestor") || $(this).hasClass("current-post-ancestor") ){
					$(this).find(".sub-menu").show();
				}
			}
		});
	}

	function fe_mainHeadline(){
		var $mainHeadline = $(".topVisual .headline");
		var $listItem = $mainHeadline.find("ul li");
		var itemLength = $listItem.length;

		if ( itemLength > 0 ){
			$mainHeadline.append('<div class="control"><button type="button" class="auto stop">�뺤�</button></div>');
			$listItem.each(function(i){
				i = i+1;
				$mainHeadline.find(".control").append('<button type="button" class="pager">'+i+'</button>')
			});
			$mainHeadline.find(".control .pager:first").addClass("current")

			var $autoBtn = $mainHeadline.find(".control .auto");
			var slider = $mainHeadline.find("ul").bxSlider({
				auto: true,
				pause:5000,
				controls: false,
				pager:false,
				autoHover:true,
				onSlideAfter: function(){
					var count = slider.getCurrentSlide();
					$mainHeadline.find(".control .pager").eq(count).addClass("current").siblings().removeClass("current");
				}
			});

			$mainHeadline.find(".control .pager").each(function(i){
				$(this).click(function(){
					slider.goToSlide(i);
				});
			});

			$autoBtn.click(function(){
				if ( $(this).hasClass("stop") ){
					slider.stopAuto();
					$(this).removeClass("stop").addClass("play");
				} else {
					slider.startAuto();
					$(this).removeClass("play").addClass("stop");
				}
			});
		}
	}

	function fe_mainFocus(){
		var $wkFocus = $(".wkFocus");
		var $focusList = $wkFocus.find(".focusList ul");
		var $focusItem = $focusList.find("li");
		var itemWidth = $focusItem.outerWidth();
		var itemLength = $focusItem.length;
		var visibleItem = 4;
		var nowPos = 0;

		$focusList.find("li:odd").addClass("odd");

		if ( itemLength > 4 ){
			$wkFocus.find(".focusList").before('<div class="control"><button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button></div>');
			var wkFocusSlider = $focusList.bxSlider({
				slideWidth: itemWidth,
				minSlides: 2,
				maxSlides: 4,
				moveSlides:1,
				infiniteLoop: false,
				controls: false,
				pager:false,
				autoHover:true
			});

			var $wkFocus = $wkFocus.find(".control");
			var $prevBtn = $wkFocus.find(".prev");
			var $nextBtn = $wkFocus.find(".next");

			$prevBtn.click(function(){
				wkFocusSlider.goToPrevSlide();
			});

			$nextBtn.click(function(){
				wkFocusSlider.goToNextSlide();
			});

			$(window).resize(function(){
				wkFocusSlider.destroySlider();
				itemWidth = $focusItem.outerWidth();
				wkFocusSlider.bxSlider({
					slideWidth: itemWidth,
					minSlides: 2,
					maxSlides: 4,
					moveSlides:1,
					infiniteLoop: false,
					controls: false,
					pager:false,
					autoHover:true
				});
			});
		}

	}

	function fe_mainLatestNews(){
		var $news = $(".recentWrap .news");
		var $latest = $news.find(".latest");
		var $list = $latest.find(".list");
		var itemLength = $list.length;
		var itemWidth = $list.outerWidth();
		var nowPage = 1;

		if ( itemLength > 1 ){
			$latest.wrap('<div class="listWrap" />').css("width", itemWidth*itemLength);
			$list.css({ "float":"left", "width":$latest.parent().width() });
			$news.find(".hwrap").append('<div class="control"><span class="page">'+nowPage+'/'+itemLength+'</span><button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button></div>');

			var $control = $news.find(".control");
			var $page = $control.find(".page");
			var $prevBtn = $control.find(".prev");
			var $nextBtn = $control.find(".next");

			$prevBtn.click(function(){
				if ( nowPage > 1 ){
					nowPage--;
					$latest.animate({ "left": -itemWidth*(nowPage-1) },300 );
					$page.text(nowPage+'/'+itemLength);
				}
			});

			$nextBtn.click(function(){
				if ( nowPage < itemLength ){
					nowPage++;
					$latest.animate({ "left": -itemWidth*(nowPage-1) },300 );
					$page.text(nowPage+'/'+itemLength);
				}
			});

			$(window).resize(function(){
				$list.css({ "width":$latest.parent().width() });
				$latest.css({ "width":$list.width()*itemLength, "left":0 });
				itemWidth = $list.outerWidth();
			});

		}
	}

  //R&D怨듭� 異붽�瑜� �꾪빐 �섏젙�덉쓬. 李쎌뿰,20150819
	function fe_mainNotice(){
		var $notice = $(".recentWrap .info .notice");
		var $control = $notice.find(".control");
		var $title = $notice.find("h2");
		var $list = $notice.find(".list");
		var itemWidth = $list.find("ul").outerWidth();
		var nowPage = 1;

		$title.each(function(i){
			var tabID = $(this).find("a").attr("href");
			$(this).css({ "float":"left", "position":"absolute", "top": $notice.css("padding-top") });
			$(tabID).wrap('<div class="listWrap" />')
			if ( i == 1 ){
				$(this).css({ "left": $title.eq(0).outerWidth()+parseInt($notice.css("padding-left"))-5 });//�먮낯 $(this).css({ "left": $title.eq(0).outerWidth()+parseInt($notice.css("padding-left")) });
			}
      //異붽��덉쓬, tab�� 3媛쒕줈 �섏뼱�섏꽌(怨듭��ы빆, BBS �숈깮怨듭�.....R&D怨듭�)
			if ( i == 2 ){
				$(this).css({ "left": $title.eq(0).outerWidth()+$title.eq(1).outerWidth()+parseInt($notice.css("padding-left"))-10 });
			}
		});

		$title.find("a").click(function(){
			var tabContent = $(this).attr("href");
			nowPage = 1;
			$list.css("left",0);
			if ( !$(this).parent().hasClass("current") ){
				$title.removeClass("current");
				$(this).parent().addClass("current");
				$list.parent().hide();
				$(tabContent).parent().show();
				tabSlider(tabContent);
			} else {
				var url = $(this).attr("data-link");
				if ( url ){
					window.location = url;
				}
			}
			return false;
		});

		function tabSlider(tabContent){
			var $tabContent = $(tabContent);
			var $listItem = $tabContent.find("ul");
			var itemLength = $listItem.length;

			if ( itemLength > 1 ){
				$tabContent.css("width", itemWidth*itemLength);
				$listItem.css({ "float":"left", "width":$list.parent().width() });
				$control.html('<span class="page">'+nowPage+'/'+itemLength+'</span><button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');

				var $page = $control.find(".page");
				var $prevBtn = $control.find(".prev");
				var $nextBtn = $control.find(".next");

				$prevBtn.click(function(){
					if ( nowPage > 1 ){
						nowPage--;
						$tabContent.animate({ "left": -itemWidth*(nowPage-1) },300 );
						$page.text(nowPage+'/'+itemLength);
					}
				});

				$nextBtn.click(function(){
					if ( nowPage < itemLength ){
						nowPage++;
						$tabContent.animate({ "left": -itemWidth*(nowPage-1) },300 );
						$page.text(nowPage+'/'+itemLength);
					}
				});
			} else {
				$control.empty();
			}
		}

		$title.eq(0).find("a").triggerHandler("click");

		$notice.find(".list.bbs ul li a").on("click", function(){
			var thisUrl = $(this).attr("href");
			window.open(thisUrl, "", "width=800px, height=400px, scrollbars=yes");
			return false;
		});

		$(window).resize(function(){
			$title.each(function(i){
				var tabID = $(this).find("a").attr("href");
				$(this).css({ "float":"left", "position":"absolute", "top": $notice.css("padding-top") });
				if ( i == 1 ){
					$(this).css({ "left": $title.eq(0).outerWidth()+parseInt($notice.css("padding-left")) });
				}
			});
			$list.find("ul").css({ "width":$list.parent().width() });
			$list.css({ "width":$list.parent().width()*$list.find("ul:visible").length, "left":-$list.parent().width()*(nowPage-1) });
			itemWidth = $list.find("ul").outerWidth();
		});
	}


/* R&D異붽��� �먮낯
	function fe_mainNotice(){
		var $notice = $(".recentWrap .info .notice");
		var $control = $notice.find(".control");
		var $title = $notice.find("h2");
		var $list = $notice.find(".list");
		var itemWidth = $list.find("ul").outerWidth();
		var nowPage = 1;

		$title.each(function(i){
			var tabID = $(this).find("a").attr("href");
			$(this).css({ "float":"left", "position":"absolute", "top": $notice.css("padding-top") });
			$(tabID).wrap('<div class="listWrap" />')
			if ( i == 1 ){
				$(this).css({ "left": $title.eq(0).outerWidth()+parseInt($notice.css("padding-left")) });
			}
		});

		$title.find("a").click(function(){
			var tabContent = $(this).attr("href");
			nowPage = 1;
			$list.css("left",0);
			if ( !$(this).parent().hasClass("current") ){
				$title.removeClass("current");
				$(this).parent().addClass("current");
				$list.parent().hide();
				$(tabContent).parent().show();
				tabSlider(tabContent);
			} else {
				var url = $(this).attr("data-link");
				if ( url ){
					window.location = url;
				}
			}
			return false;
		});

		function tabSlider(tabContent){
			var $tabContent = $(tabContent);
			var $listItem = $tabContent.find("ul");
			var itemLength = $listItem.length;

			if ( itemLength > 1 ){
				$tabContent.css("width", itemWidth*itemLength);
				$listItem.css({ "float":"left", "width":$list.parent().width() });
				$control.html('<span class="page">'+nowPage+'/'+itemLength+'</span><button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');

				var $page = $control.find(".page");
				var $prevBtn = $control.find(".prev");
				var $nextBtn = $control.find(".next");

				$prevBtn.click(function(){
					if ( nowPage > 1 ){
						nowPage--;
						$tabContent.animate({ "left": -itemWidth*(nowPage-1) },300 );
						$page.text(nowPage+'/'+itemLength);
					}
				});

				$nextBtn.click(function(){
					if ( nowPage < itemLength ){
						nowPage++;
						$tabContent.animate({ "left": -itemWidth*(nowPage-1) },300 );
						$page.text(nowPage+'/'+itemLength);
					}
				});
			} else {
				$control.empty();
			}
		}

		$title.eq(0).find("a").triggerHandler("click");

		$notice.find(".list.bbs ul li a").on("click", function(){
			var thisUrl = $(this).attr("href");
			window.open(thisUrl, "", "width=800px, height=400px, scrollbars=yes");
			return false;
		});

		$(window).resize(function(){
			$title.each(function(i){
				var tabID = $(this).find("a").attr("href");
				$(this).css({ "float":"left", "position":"absolute", "top": $notice.css("padding-top") });
				if ( i == 1 ){
					$(this).css({ "left": $title.eq(0).outerWidth()+parseInt($notice.css("padding-left")) });
				}
			});
			$list.find("ul").css({ "width":$list.parent().width() });
			$list.css({ "width":$list.parent().width()*$list.find("ul:visible").length, "left":-$list.parent().width()*(nowPage-1) });
			itemWidth = $list.find("ul").outerWidth();
		});
	}
*/
	function fe_mainSchedule(){
		var $latest = $(".recentWrap .info .schedule");
		var $list = $latest.find(".list");
		var $listItem = $list.find("ul");
		var itemLength = $listItem.length;
		var itemWidth = $listItem.outerWidth();
		var nowPage = 1;

		$list.wrap('<div class="listWrap" />').css("width", itemWidth*itemLength);
		if ( itemLength > 1 ){
			$listItem.css({ "float":"left", "width":$list.parent().width() });
			$latest.find(".hwrap").append('<div class="control"><span class="page">'+nowPage+'/'+itemLength+'</span><button type="button" class="prev" title="�댁쟾(�쒖꽌�� 醫낅즺�� 湲곗��쇰줈 �ㅺ��ㅻ뒗 �쇱젙�쒖엯�덈떎.)">�댁쟾</button><button type="button" class="next" title="�ㅼ쓬(�쒖꽌�� 醫낅즺�� 湲곗��쇰줈 �ㅺ��ㅻ뒗 �쇱젙�쒖엯�덈떎.)">�ㅼ쓬</button></div>');

			var $control = $latest.find(".control");
			var $page = $control.find(".page");
			var $prevBtn = $control.find(".prev");
			var $nextBtn = $control.find(".next");

			$prevBtn.click(function(){
				if ( nowPage > 1 ){
					nowPage--;
					$list.animate({ "left": -itemWidth*(nowPage-1) },300 );
					$page.text(nowPage+'/'+itemLength);
				}
			});

			$nextBtn.click(function(){
				if ( nowPage < itemLength ){
					nowPage++;
					$list.animate({ "left": -itemWidth*(nowPage-1) },300 );
					$page.text(nowPage+'/'+itemLength);
				}
			});

			$(window).resize(function(){
				$listItem.css({ "width":$latest.parent().width() });
				$list.css({ "width":$listItem.width()*itemLength, "left":0 });
				itemWidth = $listItem.outerWidth();
			});
		}
	}

	function fe_mainBoxBanner(){
		var $mainBoxBanner = $(".bannerZone .banner");
		var $listItem = $mainBoxBanner.find("li");
		var itemLength = $listItem.length;

		if ( itemLength > 1 ){
			$mainBoxBanner.append('<div class="control"><button type="button" class="prev">�댁쟾</button><button type="button" class="auto stop">�뺤�</button><button type="button" class="next">�ㅼ쓬</button></div>');
			var $prevBtn = $mainBoxBanner.find(".prev");
			var $nextBtn = $mainBoxBanner.find(".next");
			var $autoBtn = $mainBoxBanner.find(".auto");

			var slider = $mainBoxBanner.find("ul").bxSlider({
				auto: true,
				pause:5000,
				controls: false,
				pager:false,
				autoHover:true
			});

			$prevBtn.click(function(){
				slider.goToPrevSlide();
			});

			$nextBtn.click(function(){
				slider.goToNextSlide();
			});

			$autoBtn.click(function(){
				if ( $(this).hasClass("stop") ){
					slider.stopAuto();
					$(this).removeClass("stop").addClass("play");
				} else {
					slider.startAuto();
					$(this).removeClass("play").addClass("stop");
				}
			});
		}
	}

	function fe_mainExternalLink(){
		var $mainExternalLink = $(".otherSite");
		var $listItem = $mainExternalLink.find("li");
		var itemLength = $listItem.length;
		var itemWidth = $listItem.width();

		if ( itemLength > 2 ){
			$listItem.css("margin",0);
			$mainExternalLink.prepend('<button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');

			var $prevBtn = $mainExternalLink.find(".prev");
			var $nextBtn = $mainExternalLink.find(".next");

			var linkSlider = $mainExternalLink.find("ul").bxSlider({
				slideWidth: itemWidth,
				minSlides: 2,
				maxSlides: 5,
				moveSlides:1,
				auto: true,
				pause:5000,
				controls: false,
				pager:false,
				autoHover:true
			});

			$prevBtn.click(function(){
				linkSlider.goToPrevSlide();
			});

			$nextBtn.click(function(){
				linkSlider.goToNextSlide();
			});

			$(window).resize(function(){
				linkSlider.destroySlider();
				itemWidth = $listItem.width();
				linkSlider.bxSlider({
					slideWidth: itemWidth,
					minSlides: 2,
					maxSlides: 5,
					moveSlides:1,
					auto: true,
					pause:5000,
					controls: false,
					pager:false,
					autoHover:true
				});
			});
		}
	}

	function fe_siteLink(){
		var $trigger = $(".siteLink h2");
		var $linkList = $(".siteLink ul");

		$trigger.on("click", function(){
			if ( $(window).width() < 768 ){
				if ( !$(this).hasClass("current") ){
					$linkList.hide();
					$trigger.removeClass("current")
					$(this).addClass("current");
					$(this).next().show();
				} else {
					$linkList.hide();
					$(this).removeClass("current");
				}
			} else if ( $(window).width() < 960 ){
				if ( $linkList.is(":hidden") ){
					$linkList.show();
				} else {
					$linkList.hide();
				}
			}
		});
	}

	function fe_familySite(){
		var $familySite = $(".familySite");
		var $trigger = $familySite.find("h2");
		var $list = $familySite.find("ul");

		$trigger.on("mouseenter", function(){
			if ( !$list.is(":animated") && $list.is(":hidden")  ){
				$list.slideDown(300);
			}
		});

		$trigger.on("click focus", function(){
			if ( !$list.is(":animated") && $list.is(":visible")  ){
				$list.slideUp(300);
			} else {
				$list.slideDown(300);
			}
		});

		$familySite.on("mouseleave", function(){
			$list.stop().slideUp(300);
		});
	}

	function fe_print(){
		var $print = $(".print");

		$print.click(function(){
			window.print();
			return false;
		});
	}

	function fe_tabContent(){
		var $tabNav = $(".tabNav, .historyTab");
		var $tab = $(".tabNav ul li, .historyTab ul li");
		var $trigger = $tab.find("a");
		var $tabContent = $(".tabContent");
		var tabHeight = $tab.outerHeight();
		var currentIndex = $(".tabNav ul li.current, .historyTab ul li.current, .tabNav ul li.current_page_item, .historyTab ul li.current_page_item").index();
		var tabSlider;

		if ( currentIndex < 0 ){ currentIndex = 0 }

		$tab.each(function(){
			var thisHeight = $(this).outerHeight();
			if ( thisHeight > tabHeight ){
				tabHeight = thisHeight;
			}
		});

		$tab.each(function(){
			var thisHeight = $(this).outerHeight();
			var paddingPlug = tabHeight-thisHeight;
			var paddingTop = parseInt( $(this).find("a").css("padding-top") );
			var paddingBottom = parseInt( $(this).find("a").css("padding-bottom") );

			if ( thisHeight < tabHeight ){
				$(this).find("a").css({
					"padding-top":paddingTop+(paddingPlug/2)+"px",
					"padding-bottom":paddingBottom+(paddingPlug/2)+"px"
				});
			}
		});

		$tabNav.on("click", "ul li a", function(){
			var thisContent = $(this).attr("href");

			if ( thisContent.indexOf("#") == 0 ){
				$(this).parent().addClass("current").siblings().removeClass("current");
				$tabContent.hide();
				$(thisContent).show();
				return false;
			}

		});

		$tabContent.hide();
		$tab.find("a").eq(0).trigger("click");

		if ( $(window).width() < 768 && $tab.length > 2 ){
			tabNavSlider();
		}
		function tabNavSlider(){
			if ( !$tabNav.find("button").length ){
				$tabNav.append('<button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');
			}

			tabSlider = $tabNav.find("ul").bxSlider({
				slideWidth: $tab.outerWidth(),
				minSlides: 2,
				maxSlides: 2,
				moveSlides:1,
				startSlide: currentIndex,
				controls: false,
				pager:false,
				autoHover:true
			});
		}

		$tabNav.on("click", ".prev", function(){
			tabSlider.goToPrevSlide();
		});

		$tabNav.on("click", ".next", function(){
			tabSlider.goToNextSlide();
		});


		$(window).resize(function(){
			if ( $(window).width() < 768 && $tab.length > 2 ){
				if ( $tabNav.find(".bx-wrapper").length ){
					tabSlider.destroySlider();
					tabSlider.bxSlider({
						slideWidth: $tab.outerWidth(),
						minSlides: 2,
						maxSlides: 2,
						moveSlides:1,
						startSlide: currentIndex,
						controls: false,
						pager:false,
						autoHover:true
					});
				} else {
					tabNavSlider();
				}
			} else {
				if ( $tabNav.find(".bx-wrapper").length ){
					tabSlider.destroySlider();
				}
				$tabNav.find("ul, ul li").removeAttr("style");
			}
		});
	}

	function fe_organization(){
		var $trigger = $(".organization ul li .button");

		$trigger.parent().find(">ul>li>ul").hide();
		$trigger.parent().find(">ul.hide").hide();
		$trigger.click(function(){
			var $hideList = $(this).parent().find(">ul.hide")
			var $subList = $(this).parent().find(">ul>li>ul");
			if ( !$(this).hasClass("current") ){
				$(this).addClass("current");
				$subList.slideDown(300);
				if ( $hideList ){
					$hideList.slideDown(300);
				}
			} else {
				$(this).removeClass("current");
				$subList.slideUp(300);
				if ( $hideList ){
					$hideList.slideUp(300);
				}
			}
		});

	}

	function fe_googleMap() {
		var $map = $(".googleMap");

		$map.each(function(i){
			var location = $(this).attr("data-location").split(',');
			$(this).attr("id","googleMap"+i);
			var map_x = parseFloat(location[0]);
			var map_y = parseFloat(location[1]);

			var latlng = new google.maps.LatLng(map_x, map_y);
			var myOptions = {
				zoom: 17,
				center:latlng,
				scrollwheel: false,
				draggable:true,
				disableDoubleClickZoom: true,
				mapTypeId: google.maps.MapTypeId.ROADMAP
			};

			var map = new google.maps.Map(document.getElementById("googleMap"+i), myOptions);
//			var image = '/wp-content/themes/wku/common/img/ico_google_map.png';
			var marker = new google.maps.Marker({
				map:map,
				draggable:false,
				animation: google.maps.Animation.DROP,
				position: latlng
//				icon: image
			});
		});
	}

	function fe_campusMap(){
		var $campusMap = $(".campusMap");
		var $mapIco = $campusMap.find(".map ul li");
		var $group = $campusMap.find(".group ul li");
		var $trigger = $campusMap.find(".list ul li");
		var $mapInfo = $campusMap.find(".mapInfo");

		$mapIco.hide();
		$campusMap.find(".list ul").height("100%");

		$trigger.find("a").on("click", function(){
			var mapId = $(this).attr("href");
			var icoClass = mapId.replace("#",'.');

			$(this).parent().addClass("current").siblings().removeClass("current");
			$mapIco.hide();
			$mapInfo.hide();
			$(mapId).show();
			$(icoClass).show();

			if ( $(window).width() < 768 ){
				var $map = $(".campusMap .mapArea .map");
				var offsetTop = $(icoClass).position().top;
				var offsetLeft = $(icoClass).position().left;
				var nowY = parseInt( $map.css("top") );
				var nowX = parseInt( $map.css("left") );

				$map.animate({
					"margin":0,
					"top":-offsetTop+180,
					"left":-offsetLeft+($campusMap.outerWidth()/2)-20
				}, 300);

				$.getScript("/wp-content/themes/wku/common/js/jquery.ui.touch-punch.min.js").done(function() {
					$map.draggable();
				});
			}

			return false;
		});

		$trigger.hide();

		$group.find("button").on("click", function(){
			var sortName = $(this).attr("data-sort");
			$(this).parent().addClass("current").siblings().removeClass("current");;
			if ( sortName == "total" ){
				$trigger.show();
			} else {
				$trigger.hide();
				$trigger.each(function(){
					if ( $(this).hasClass(sortName) ){
						$(this).show();
					}
				});
			}
			$campusMap.find(".list ul li:visible").eq(0).find("a").triggerHandler("click");
		});

		$group.eq(0).find("button").triggerHandler("click");
	}

	function fe_stepList(){
		console.log(3);
		var $stepList = $(".stepList");

		$stepList.each(function(i){
			if ( $(window).width() > 767 && !$(this).hasClass("auto") ){
				var $listItem = $(this).find(">ul>li");
				var listHeight = $listItem.outerHeight();
				var $divide = $listItem.find(".divide");
				var divideHeight = $divide.outerHeight();

				$(this).addClass("auto");
				$divide.each(function(){
					var thisHeight = $(this).outerHeight();
					if ( thisHeight > divideHeight ) divideHeight = thisHeight;
				});

				$divide.each(function(){
					var thisHeight = $(this).outerHeight();

					$(this).css({
						"padding-top":(divideHeight-thisHeight)/2,
						"padding-bottom":(divideHeight-thisHeight)/2
					});
				});

				$listItem.each(function(){
					var thisHeight = $(this).outerHeight();
					if ( thisHeight > listHeight ) listHeight = thisHeight;
				});

				$listItem.each(function(){
					var thisHeight = $(this).outerHeight();

					$(this).find(".box").css({
						"padding-top":(listHeight-thisHeight)/2,
						"padding-bottom":(listHeight-thisHeight)/2
					});
				});
			}
		});

		$(window).resize(function(){
			if ( $(window).width() > 767 && !$(".stepList").hasClass("auto") ){
				fe_stepList();
			} else if ( $(window).width() < 768 && $(".stepList").hasClass("auto") ){
				$(".stepList").removeClass("auto");
				$(".stepList").find("li .box").removeAttr("style");
			}
		});
	}

	function fe_tabGallery(){
		var $tabGallery = $(".tabGallery");

		$tabGallery.each(function(){
			var $trigger = $(this).find(".galleryNav ul li");
			var $galleryContent = $(this).find(".galleryContent");

			$(this).on("click",".galleryNav ul li a", function(){
				var thisCont = $(this).attr("href");

				$(this).parent().addClass("current").siblings().removeClass("current");
				$galleryContent.hide();
				$(thisCont).show();
				return false;
			});

			$galleryContent.each(function(i){
				var $imageList = $(this).find(".images");
				var imgLength = $imageList.find("ul li").length;

				if ( imgLength > 1 ){
					$imageList.append('<button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');

					var $prevBtn = $imageList.find(".prev");
					var $nextBtn = $imageList.find(".next");

					var gallerySlider = $imageList.find("ul").bxSlider({
						auto: true,
						pause:5000,
						controls: false,
						pager:false,
						autoHover:true
					});

					$prevBtn.click(function(){
						gallerySlider.goToPrevSlide();
					});

					$nextBtn.click(function(){
						gallerySlider.goToNextSlide();
					});
				}
			});

			$trigger.eq(0).find("a").trigger("click");
		});
	}

	function fe_dataSort(){
		$select = $(".dateSort");
		$trigger = $select.find("button");
		$list = $select.find("ul");

		$list.hide();
		$trigger.on("mouseenter focus", function(){
			if ( !$list.is(":visible") ){
				$list.slideDown(300);
			}
		});

		$select.on("mouseleave", function(){
			$list.slideUp(300);
		});
	}

	function fe_selectBox(){
		var $selectBox = $(".selectBox");

		$selectBox.each(function(){
			var title = $(this).attr('title');

			$(this).wrapAll('<div class="selectWrap"></div>');
			if( $("option:selected", this).val() != "" ) title = $("option:selected",this).text();
			$(this).after('<div class="selectTrigger">' + title + '</div>')
			.change(function(){
				val = $('option:selected',this).text();
				$(this).next().text(val);
			})
		});
	}

	function fe_calendarSlider(){
		var $calendarNav = $(".academicCalendar .nav ul");
		var $navItem = $calendarNav.find("li");
		var itemWidth = $navItem.outerWidth();
		var currentIndex = $calendarNav.find("li.current").index()-1;
		if ( currentIndex < 0 ){ currentIndex == 0 }

		var $prevBtn = $(".academicCalendar .prevMonth");
		var $nextBtn = $(".academicCalendar .nextMonth");

		if ( $(window).width() < 768 ){
			var calendarSlider = $calendarNav.bxSlider({
				slideWidth: itemWidth,
				minSlides: 4,
				maxSlides: 4,
				moveSlides:1,
				startSlide:currentIndex,
				controls: false,
				pager:false,
				autoHover:true
			});
		}

		$prevBtn.on("click", function(){
			calendarSlider.goToPrevSlide();
		});

		$nextBtn.on("click", function(){
			calendarSlider.goToNextSlide();
		});

		$(window).resize(function(){
			if ( $(window).width() < 768 ){
				if ( !$(".academicCalendar").find(".bx-wrapper").length ){
					calendarSlider = $calendarNav.bxSlider({
						slideWidth: itemWidth,
						minSlides: 4,
						maxSlides: 4,
						moveSlides:1,
						startSlide:currentIndex,
						controls: false,
						pager:false,
						autoHover:true
					});
				}
			} else {
				if ( $(".academicCalendar").find(".bx-wrapper").length ){
					calendarSlider.destroySlider();
				}
				$calendarNav.removeAttr("style");
				$calendarNav.find("li").removeAttr("style");
			}
		});
	}

	function fe_mobieSlider(){
		var $sliderNav = $(".galleryNav, .campusMap .group");
		var $navItem = $sliderNav.find("ul li");
		var itemWidth = $navItem.outerWidth();

		if ( $(window).width() < 768 ){
			$sliderNav.append('<button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');
			var mobieSlider = $sliderNav.find("ul").bxSlider({
				slideWidth: itemWidth,
				minSlides: 2,
				maxSlides: 2,
				moveSlides:1,
				controls: false,
				pager:false,
				autoHover:true
			});
		}

		$sliderNav.on("click", ".prev", function(){
			mobieSlider.goToPrevSlide();
		});

		$sliderNav.on("click", ".next", function(){
			mobieSlider.goToNextSlide();
		});

		$(window).resize(function(){
			if ( $(window).width() < 768 ){
				if ( !$sliderNav.find(".bx-wrapper").length ){
					if ( !$sliderNav.find("button").length ){
						$sliderNav.append('<button type="button" class="prev">�댁쟾</button><button type="button" class="next">�ㅼ쓬</button>');
					}
					mobieSlider = $sliderNav.find("ul").bxSlider({
						slideWidth: itemWidth,
						minSlides: 2,
						maxSlides: 2,
						moveSlides:1,
						controls: false,
						pager:false,
						autoHover:true
					});
				}
			} else {
				if ( $sliderNav.find(".bx-wrapper").length ){
					mobieSlider.destroySlider();
				}
				$sliderNav.find("ul").removeAttr("style");
				$sliderNav.find("li").removeAttr("style");
			}
		});
	}

	function fe_apiPopup(){
		var $target = $(".apiPop");

		console.log( $target );

		$target.each(function(){
			$(this).on("click", function(){
				var thisUrl = $(this).attr("href");
				window.open(thisUrl, "", "width=800px, height=400px, scrollbars=yes");
				return false;
			});
		});
	}

	function fe_pageTop(){
		var $pageTopBtn = $(".pageTop");

		$pageTopBtn.click(function(){
			$("html,body").animate({ scrollTop: $("#wrap").offset().top},500 );
		});
	}

	function fe_mobileMenu(){
		var $mobileMenu2 = $("#mobileMenu2");

		$mobileMenu2.on("click", "a", function(){
			var name = $(this).text(),
				state = false;

			$("html, body").animate({ scrollTop:0 },100 );
			$("#gnb>ul>li>a").each(function(){
				if ( $(this).text() == name ){
					state = true;
					$trigger = $(this);
				}
			});

			if ( state == true ){
				$("#header .util .gnbBtn").triggerHandler("click");
				$trigger.triggerHandler("click");
				return false;
			}
		});
	}

	function fe_pageScroll(){
		var $hgroup = $(".hgroup");

		$(window).load(function(){
			if ( parseInt($("#gnb").css("right")) < 0 ){
				$("html, body").animate({ scrollTop:$hgroup.offset().top },300 );
			}
		});
	}

	function fe_main_slider(){
		var $org_html = $("#slider_container").html();

		function ScaleSlider() {
			var parentWidth = $('.topVisual').width();
			var effect = $('.topVisual').attr("data-effect");
			var effect_array = effect.split(',');

			for ( i =0; i < effect_array.length; i++){
				if ( effect_array[i] == "fade" ){
					effect_array[i] = {$Duration:1200,$Opacity:2};
				} else if ( effect_array[i] == "slide" ){
					effect_array[i] = {$Duration:1200,x:1,$Easing:{$Left:$JssorEasing$.$EaseInOutQuart,$Opacity:$JssorEasing$.$EaseLinear},$Opacity:2,$Brother:{$Duration:1200,x:-1,$Easing:{$Left:$JssorEasing$.$EaseInOutQuart,$Opacity:$JssorEasing$.$EaseLinear},$Opacity:2}};
				} else if ( effect_array[i] == "expand" ){
					effect_array[i] = {$Duration:800,$Delay:300,$Cols:8,$Rows:4,$Clip:15,$Formation:$JssorSlideshowFormations$.$FormationSquare,$Easing:$JssorEasing$.$EaseInQuad};
				}
			}
			var _CaptionTransitions = [];
				_CaptionTransitions["text_effect_1"] = {$Duration:600,x:-0.6,$Easing:{$Left:$JssorEasing$.$EaseInOutSine},$Opacity:2};
		        _CaptionTransitions["text_effect_2"] = {$Duration:300,$Easing:$JssorEasing$.$EaseInCubic,$Opacity:2},
		        _CaptionTransitions["text_effect_3"] = {$Duration:300};
			var _SlideshowTransitions = effect_array;
			var options = {
				$FillMode: 2,
				$AutoPlay: true,
				$AutoPlayInterval: 8000,
				$SlideWidth: parentWidth,
				$SlideshowOptions: {
		            $Class: $JssorSlideshowRunner$,
		            $Transitions: _SlideshowTransitions,
		            $TransitionsOrder: 1,
		            $ShowLink: true
		       },
		       $CaptionSliderOptions: {
		            $Class: $JssorCaptionSlider$,
		            $CaptionTransitions: _CaptionTransitions,
		            $PlayInMode: 1,
		            $PlayOutMode: 3
		        }
			};

			$("#slider_container").remove();
			$(".topVisual").prepend('<div id="slider_container" class="slider_container"></div>');
			$("#slider_container").html($org_html).find(".caption").each(function(){
				if ( $(this).hasClass("left") ){
					$(this).css({
						"margin-top": -$(this).outerHeight()/2,
						"margin-left": $(".wkFocus .inner").offset().left
					});
				} else if ( $(this).hasClass("center") ){
					$(this).css({
						"margin-top": -$(this).outerHeight()/2,
						"margin-left": ($(".topVisual").width()/2)-($(this).width()/2)
					});
				} else if ( $(this).hasClass("right") ){
					$(this).css({
						"margin-top": -$(this).outerHeight()/2,
						"margin-left": $(".topVisual").width()-$(this).outerWidth()-$(".wkFocus .inner").offset().left
					});
				}
	        });
	        $(".topVisual .controls .control").removeClass("stop");

			setTimeout(function(){
				var jssor_slider = new $JssorSlider$('slider_container', options);
				$(".topVisual").addClass("on");

				$(".topVisual").on("click", ".controls .control", function(){
					if ( $(this).hasClass("stop") ){
						$(this).removeClass("stop");
						jssor_slider.$Play();
					} else {
						$(this).addClass("stop");
						jssor_slider.$Pause();
					}
				});

				$(".topVisual").on("click", ".controls .prev", function(){
					jssor_slider.$Prev();
				});

				$(".topVisual").on("click", ".controls .next", function(){
					jssor_slider.$Next();
				});
			},100 );
	    }

		ScaleSlider();
	    $(window).bind("resize", ScaleSlider);
	    $(window).bind("orientationchange", ScaleSlider);
	}

	/* Common */
	fe_gnb();
	fe_utilNav();
	fe_searchBox();
	fe_lnb();
	fe_siteLink();
	fe_familySite();
	fe_print();
	fe_pageTop();
	fe_mobileMenu();

	/* Main */
	if ( $("#wrap").hasClass("home") ){
		fe_main_slider();
		fe_mainHeadline();
		fe_mainFocus();
		fe_mainLatestNews();
		fe_mainNotice();
		fe_mainSchedule();
		fe_mainBoxBanner();
		fe_mainExternalLink();
	} else {
		fe_pageScroll();
	}

	function fe_jw(){
		$videos = $(".video-rtmp");

		$videos.each(function(i){
			$(this).attr("id", "movie"+i);
			$url = $(this).attr("data-url");
			jwplayer("movie"+i).setup({
			    playlist: [{
			        image: "http://www.wku.ac.kr/wp-content/themes/wku/common/jwplayer/cover_wku.png",
			        sources: [{
			            file: $url
			        },{
			            file: $url
			        }]
			    }],
			    width: $(this).width(),
			    height: ($(this).width()/16)*9,
			    primary: "flash"
			});
		});
	}

	/* Content */
	if ( $(".tabNav, .historyTab").length ) fe_tabContent();
	if ( $(".organization").length ) fe_organization();
	if ( $(".googleMap").length ) fe_googleMap();
	if ( $(".campusMap").length ) fe_campusMap();
	if ( $(".stepList").length ) fe_stepList();
	if ( $(".tabGallery").length ) fe_tabGallery();
	if ( $(".dateSort").length ) fe_dataSort();
	if ( $(".selectBox").length ) fe_selectBox();
	if ( $(".academicCalendar").length ) fe_calendarSlider();
	if ( $(".galleryNav, .campusMap .group").length ) fe_mobieSlider();
	if ( $(".apiPop").length ) fe_apiPopup();
	if ( $(".video-rtmp").length ) fe_jw();


})(jQuery);