"use strict";function _toConsumableArray(arr){return _arrayWithoutHoles(arr)||_iterableToArray(arr)||_nonIterableSpread()}function _nonIterableSpread(){throw new TypeError("Invalid attempt to spread non-iterable instance")}function _iterableToArray(iter){if(Symbol.iterator in Object(iter)||"[object Arguments]"===Object.prototype.toString.call(iter))return Array.from(iter)}function _arrayWithoutHoles(arr){if(Array.isArray(arr)){for(var i=0,arr2=new Array(arr.length);i<arr.length;i++)arr2[i]=arr[i];return arr2}}!function($){$.fn.mdbEditor=function(options){var addSomeClasses=function addSomeClasses(e,classes){return e.addClass(classes)},defaultsFx=function defaultsFx(e,type,element,property){$(e).find("table ".concat(type)).css(element,property)},defaults={headerLength:6,evenTextColor:"#000",oddTextColor:"#000",bgEvenColor:"",bgOddColor:"",thText:"",thBg:"",modalEditor:!1,bubbleEditor:!1,contentEditor:!1,rowEditor:!1,bubblePositionX:80,bubblePositionY:0};return options=$.extend(defaults,options),this.map(function(i,e){var $tableId=$("#".concat($(e).closest(".wrapper-editor").find("table tbody").parent().attr("id"))),$DataTable=$tableId.DataTable(),$wrapperEditor=$tableId.closest(".wrapper-editor"),editorOptions={headerLength:options.headerLength,evenTextColor:options.evenTextColor,oddTextColor:options.oddTextColor,bgEvenColor:options.bgEvenColor,bgOddColor:options.bgOddColor,thText:options.thText,thBg:options.thBg,modalEditor:options.modalEditor,rowEditor:options.rowEditor,bubbleEditor:options.bubbleEditor,contentEditor:options.contentEditor,bubblePositionX:options.bubblePositionX,bubblePositionY:options.bubblePositionY},startedStyleFunction=function startedStyleFunction(){defaultsFx($wrapperEditor,"tr.even","color",_toConsumableArray(editorOptions.evenTextColor).join("")),defaultsFx($wrapperEditor,"tr.odd","color",_toConsumableArray(editorOptions.oddTextColor).join("")),defaultsFx($wrapperEditor,"tr.even","background-color",_toConsumableArray(editorOptions.bgEvenColor).join("")),defaultsFx($wrapperEditor,"tr.odd","background-color",_toConsumableArray(editorOptions.bgOddColor).join("")),defaultsFx($wrapperEditor,"th","color",_toConsumableArray(editorOptions.thText).join("")),defaultsFx($wrapperEditor,"th","background-color",_toConsumableArray(editorOptions.thBg).join(""))},sortingTable=function sortingTable(){startedStyleFunction()};if(startedStyleFunction(),$wrapperEditor.find("table th, .dataTables_paginate").on("click",sortingTable),!0===options.modalEditor){var $selectedRow,$createShowP=$wrapperEditor.find(".createShowP"),$buttonEdit=$wrapperEditor.find(".buttonEdit"),$buttonDelete=$wrapperEditor.find(".buttonDelete"),$buttonAddFormWrapper=$wrapperEditor.find(".buttonAddFormWrapper"),$buttonEditWrapper=$wrapperEditor.find(".buttonEditWrapper"),$editInsideWrapper=$wrapperEditor.find(".editInsideWrapper"),$deleteButtonsWrapper=$wrapperEditor.find(".deleteButtonsWrapper"),editInside=$wrapperEditor.find(".editInside"),trColorSelected=".tr-color-selected",addNewRows=function addNewRows(){for(var $newRow=[],_i=0;_i<editorOptions.headerLength;_i++)$newRow.push($wrapperEditor.find(".addNewInputs input").eq(_i).val());$DataTable.row.add($newRow).draw()},btnToModalAdd=function btnToModalAdd(){$wrapperEditor.find(".addNewInputs input").removeClass("valid").val(""),$wrapperEditor.find(".addNewInputs label").removeClass("active")},addColorToTr=function addColorToTr(e){return $(e.target).parent().not("thead tr").not("tfoot tr").toggleClass("tr-color-selected").siblings().removeClass("tr-color-selected")},toggleDisabledToButtons=function toggleDisabledToButtons(e){$selectedRow=$(e.target).parent(),$(e.target).parent().not("thead tr").not("tfoot tr").hasClass("tr-color-selected")?($buttonEdit.prop("disabled",!1),$buttonDelete.prop("disabled",!1),$createShowP.html("1 row selected")):$("tr").hasClass("tr-color-selected")||($buttonEdit.prop("disabled",!0),$buttonDelete.prop("disabled",!0),$createShowP.html("0 row selected"))},buttonEditInput=function buttonEditInput(){for(var _i2=0;_i2<editorOptions.headerLength;_i2++)$DataTable.row($wrapperEditor.find(".modalEditClass input").eq(_i2).val($DataTable.cell($selectedRow,_i2).data()))},addClassActiveToLabel=function addClassActiveToLabel(){for(var _i3=0;_i3<editorOptions.headerLength;_i3++)""!==$wrapperEditor.find(".modalEditClass input").eq(_i3).val()&&$wrapperEditor.find(".modalEditClass label").eq(_i3).addClass("active")},buttonEditInside=function buttonEditInside(){for(var _i4=0;_i4<editorOptions.headerLength;_i4++)$DataTable.cell($(trColorSelected).find("td").eq(_i4)).data($wrapperEditor.find(".modalEditClass input").eq(_i4).val())},removeColorClassFromTr=function removeColorClassFromTr(){return $wrapperEditor.find(".tr-color-selected").removeClass("tr-color-selected")},disabledButtons=function disabledButtons(){$buttonEdit.prop("disabled",!0),$buttonDelete.prop("disabled",!0)},selectedZeroRowsNews=function selectedZeroRowsNews(){$createShowP.html("0 row selected"),$DataTable.draw(!1)},buttonDeleteYes=function buttonDeleteYes(){$buttonEdit.prop("disabled",!0),$buttonDelete.prop("disabled",!0),$createShowP.html("0 row selected"),$DataTable.row($(trColorSelected)).remove().draw()},bindEvents=function bindEvents(){$buttonAddFormWrapper.on("click",".buttonAdd",addNewRows),$wrapperEditor.on("click","tr",addColorToTr),$wrapperEditor.on("click","tr",toggleDisabledToButtons),$buttonEditWrapper.on("click",$buttonEdit,buttonEditInput),$buttonEditWrapper.on("click",$buttonEdit,addClassActiveToLabel),$deleteButtonsWrapper.on("click",".btnYesClass",buttonDeleteYes),$editInsideWrapper.on("click",editInside,buttonEditInside),$editInsideWrapper.on("click",editInside,removeColorClassFromTr),$editInsideWrapper.on("click",editInside,disabledButtons),$editInsideWrapper.on("click",editInside,selectedZeroRowsNews),$(".wrapperToBtnModalAdd").on("click",".btnToModalAdd",btnToModalAdd)};$wrapperEditor.find(".modalEditClass").on("keypress",function(e){13===e.which&&(buttonEditInside(editInside),$(editInside).trigger("click"),removeColorClassFromTr(),disabledButtons(),selectedZeroRowsNews())}),$wrapperEditor.find(".addNewInputs").on("keypress",function(e){13===e.which&&$(".buttonAdd").click()}),$wrapperEditor.find(".modalDeleteTarget").on("keypress",function(e){13===e.which&&$(".btnYesClass").click()}),bindEvents()}if(!0===options.rowEditor){var editRow=".editRow",saveRow=".saveRow",tdLast="td:last",$removeColumns=$(".removeColumns"),addNewColumn=".addNewColumn",$buttonWrapper=$(".buttons-wrapper"),$closeByClick=$(".closeByClick"),$showForm=$(".showForm");$wrapperEditor.find(".buttons-wrapper button").prop("disabled",!1);var addNewTr=function addNewTr(e){$(e.target).parents().eq(1).map(function(i,event){$(event).find("tr").map(function(i,ev){$(ev).find(tdLast).not(".td-editor").after('<td class="text-center td-editor" style="border-top: 1px solid #dee2e6; border-bottom:1px solid #dee2e6"><button class="btn btn-sm editRow btn-sm btn-teal"><i class="far fa-edit"></i></button></td>')})})},removeDisabledButtons=function removeDisabledButtons(){var $removeColumns=$wrapperEditor.find(".removeColumns");$wrapperEditor.find("td").hasClass("td-editor")&&$removeColumns.prop("disabled",!1)},editRowAndAddClassToTr=function editRowAndAddClassToTr(e){for(var $closestTrTd=$(e.target).closest(".wrapper-editor tr").find("td"),$closestTrEdit=$(e.target).closest(".wrapper-editor tr").find(editRow),divWrapper='<div class="d-flex justify-content-center div-to-remove"></div>',editButton='<td class="text-center td-editor td-yes" style="border:none"><button class="btn btn-sm btn-danger deleteRow" style="cursor:pointer;"><i class="fas fa-trash-alt"></i></b></td>',saveButton='<td class="text-center td-editor td-yes" style="border:none"><button class="btn btn-sm btn-primary saveRow" style="cursor:pointer;"><i class="fas fa-check"></i></button></td>',_i5=0;_i5<editorOptions.headerLength;_i5++)$closestTrTd.eq(_i5).html('<input type="text" class="val'.concat(_i5,' form-control" value="').concat($closestTrTd.eq(_i5).text(),'">'));$closestTrEdit.after($(divWrapper).append(saveButton,editButton)),$wrapperEditor.on("click",".deleteRow",function(){$wrapperEditor.find(".showForm, .closeByClick").removeClass("d-none")})},clickBtnCBCaddDnone=function clickBtnCBCaddDnone(e){addSomeClasses($(e.target),"d-none"),addSomeClasses($showForm,"d-none")},addDnoneByClickBtns=function addDnoneByClickBtns(){addSomeClasses($closeByClick,"d-none"),addSomeClasses($showForm,"d-none")},addColorClassAndPy=function addColorClassAndPy(e){var $closestTr=$(e.target).closest("tr");addSomeClasses($closestTr,"tr-color-selected"),addSomeClasses($closestTr.find("td").not(".td-editor"),"py-5")},addDisabledButtonsByEditBtn=function addDisabledButtonsByEditBtn(e){$(e.target).prop("disabled",!0),$(e.target).closest(".wrapper-editor").find($removeColumns).prop("disabled",!0)},saveRowAndRemovePy=function saveRowAndRemovePy(e){for(var $closestTr=$(e.target).closest("tr"),_i6=0;_i6<editorOptions.headerLength;_i6++)$DataTable.cell($closestTr.find("td").eq(_i6)).data($closestTr.find(".val"+_i6).val());$closestTr.find("td").removeClass("py-5")},removeDisabledColorAdnTdYes=function removeDisabledColorAdnTdYes(e){var $closestTr=$(e.target).closest("tr");$closestTr.find(editRow).prop("disabled",!1),$closestTr.removeClass("tr-color-selected"),$closestTr.find(".td-yes").remove(),$DataTable.draw(!1),$wrapperEditor.find("tbody tr").hasClass("tr-color-selected")||$wrapperEditor.find(".removeColumns").prop("disabled",!1)},saveRowClickRemoveDiv=function saveRowClickRemoveDiv(){$(this).find(".div-to-remove").remove()},removeColorInTrAndDraw=function removeColorInTrAndDraw(){$DataTable.row($wrapperEditor.find("tr.tr-color-selected")).remove().draw(!1),$wrapperEditor.find("tbody tr").hasClass("tr-color-selected")?$wrapperEditor.find($removeColumns).prop("disabled",!0):$wrapperEditor.find($removeColumns).prop("disabled",!1)},removeSelectedButtonsFromRow=function removeSelectedButtonsFromRow(){if($wrapperEditor.find("tbody tr").hasClass("tr-color-selected"))return $wrapperEditor.find(".removeColumns").attr("disabled",!0),!1;$wrapperEditor.find(".td-editor").remove(),$wrapperEditor.find(".tr-color-selected").remove(),$DataTable.draw(!1)},_bindEvents;(function _bindEvents(){$buttonWrapper.on("click",addNewColumn,addNewTr),$buttonWrapper.on("click",addNewColumn,removeDisabledButtons),$wrapperEditor.on("click",editRow,editRowAndAddClassToTr),$wrapperEditor.on("click",editRow,addColorClassAndPy),$wrapperEditor.on("click",editRow,addDisabledButtonsByEditBtn),$wrapperEditor.on("click",saveRow,saveRowAndRemovePy),$wrapperEditor.on("click",saveRow,removeDisabledColorAdnTdYes),$wrapperEditor.on("click",saveRow,saveRowClickRemoveDiv),$(".buttonYesNoWrapper").on("click",".btnYes",removeColorInTrAndDraw),$buttonWrapper.on("click",".removeColumns",removeSelectedButtonsFromRow),$showForm.on("click",".btnYes, .button-x, .btnNo",addDnoneByClickBtns),$closeByClick.on("click",clickBtnCBCaddDnone)})(),!0===$closeByClick.hasClass("d-none")&&$(document).keyup(function(e){27===e.keyCode&&(addSomeClasses($closeByClick,"d-none"),addSomeClasses($showForm,"d-none"))}),$wrapperEditor.find(".buttons-wrapper").on("click",".addNewRows",function(e){for(var $newRow=[],_i7=0;_i7<editorOptions.headerLength;_i7++)$newRow.push($(e.target).val());$("#".concat($(e.target).closest(".wrapper-editor").find("table").attr("id"))).DataTable().row.add($newRow).draw()})}if(!0===options.contentEditor){$wrapperEditor.find("table tbody").addClass("disabled-table");var toggleBtnAndInputs=function toggleBtnAndInputs(){$wrapperEditor.find("tbody").toggleClass("disabled-table"),$(this).find(".fa-toggle-off").toggleClass("fa-toggle-on"),$wrapperEditor.find("table tbody").hasClass("disabled-table")?$wrapperEditor.find("input").prop("disabled",!0):$wrapperEditor.find("input").prop("disabled",!1)},clickOnTd=function clickOnTd(ev){var focusInput=function focusInput(e){return $(e).focus()},$this=$(this),$eTarget=$(ev.target),$dtHeadTitle=$($wrapperEditor.find("thead th")[$DataTable.cell($this).index().column]).text().replace(/\s/g,"").toLowerCase();if("".concat($dtHeadTitle).includes("date")&&!$this.children().hasClass("mdb-content-dynamic-input")?($this.html("<input type='date' class='form-control mdb-content-dynamic-input' value='".concat($this.text(),"'/> <span class='d-none dynamic-span'>").concat($this.text(),"</span>")),focusInput(".mdb-content-dynamic-input")):$this.children().hasClass("mdb-content-dynamic-input")||($this.html("<input type='text' class='form-control mdb-content-dynamic-input' value='".concat($this.text(),"'/> <span class='d-none dynamic-span'>").concat($this.text(),"</span>")),focusInput(".mdb-content-dynamic-input")),$wrapperEditor.find("td").children().length>2){var $dtData=$wrapperEditor.find(".mdb-content-dynamic-input").parent().not($eTarget).html($wrapperEditor.find(".mdb-content-dynamic-input").not($eTarget.children()).val());$DataTable.cell($dtData).data($dtData.text()).draw(),focusInput(".mdb-content-dynamic-input")}},keyupEvents=function keyupEvents(ev){var $evTarget=$(ev.target);13===ev.which&&$DataTable.cell($evTarget.parent()).data($evTarget.val()).draw(!0),27===ev.which&&$DataTable.cell($evTarget.parent()).data($evTarget.next().text()).draw(!0)},saveTdWhenSorting=function saveTdWhenSorting(){for(var $DataTableNodeTd=$DataTable.column($(this)).nodes(),_i8=0;_i8<$DataTableNodeTd.length;_i8++)$($DataTableNodeTd[_i8]).children().length>1&&$DataTable.cell($DataTableNodeTd[_i8]).data($($DataTableNodeTd[_i8]).children().closest(".mdb-content-dynamic-input").val()).draw(!0)},_addNewRows=function _addNewRows(){for(var $newRow=[],_i9=0;_i9<options.headerLength;_i9++)$newRow.push($wrapperEditor.find("thead th").eq(_i9).val());$wrapperEditor.find("tbody td").hasClass("disabled-table")?$($DataTable.row.add($newRow).draw(!0).nodes()).children().addClass("disabled-table"):$DataTable.row.add($newRow).draw(!0)},removeFirstRowOrSelected=function removeFirstRowOrSelected(){$wrapperEditor.find(".mdb-content-dynamic-input").length>0?$DataTable.row($($wrapperEditor.find(".mdb-content-dynamic-input").parents().eq(1))).remove().draw(!0):$DataTable.row().remove().draw(!0)},_bindEvents2;(function _bindEvents2(){$wrapperEditor.find(".buttons-wrapper").on("click",".add-content-edit",toggleBtnAndInputs),$wrapperEditor.find("table").not("thead, tfoot").on("click","td",clickOnTd),$wrapperEditor.not("thead, tfoot").on("keyup click",".mdb-content-dynamic-input",keyupEvents),$wrapperEditor.find(".buttons-wrapper").on("click",".remove-first-tr",removeFirstRowOrSelected),$wrapperEditor.find(".buttons-wrapper").on("click",".add-new-row",_addNewRows),$wrapperEditor.on("click","thead th",saveTdWhenSorting)})()}if(!0===options.bubbleEditor){$wrapperEditor.find(".buttons-wrapper button").prop("disabled",!1);var nth=["","Second ","Third ","Fourth ","Fifth ","Sixth ","Seventh ","Eighth","Ninth ","Tenth ","Eleventh ","Twelth ","Thirteen ","Fourteenth ","Fifteenth ","Sixteenth","Seventeenth","Eighteenth","Nineteenth"],$bubbleMainDiv=$("<div>",{class:"m-3 bubble-main d-flex-not-important flex-column"}),$document=$(document),inWords=function inWords(num){if((num=num.toString()).length>9)return"overflow";var n=("000000000"+num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);if(n){var str="";return str+=0!=n[5]?(""!=str?"and ":"")+(nth[Number(n[5])]||[n[5][0]]+" "+nth[n[5][1]]):""}};$wrapperEditor.find(".buttons-wrapper").on("click",".add-bubble-edit",function(){var $this=$(this);$this.children().hasClass("fa-toggle-off")?($this.not(".fas").html('<i class="fas fa-toggle-on ml-1"></i>'),$wrapperEditor.find("table tbody").removeClass("disabled-table"),$wrapperEditor.find("table td").addClass("bubble-td-cursor"),$wrapperEditor.find("table").not("thead, tfoot").on("click","td",function(ev){var $evTarget=$(ev.target),$dtHeadColumText=$($wrapperEditor.find("table thead th")[$DataTable.cell($evTarget).index().column]).text().replace(/\s/g,""),bubbleMainClass="m-3 p-2 d-flex-not-important justify-content-center flex-column text-left bubble-inside",btnUpadteWithWrapper="<div class='wrapper-btn-bubble-update text-center'><button class='btn btn-sm btn-rounded btn-primary mt-3 btn-bubble-update'>Update <i class='ml-1 far fa-edit'></i></button><button class='btn btn-sm btn-rounded btn-danger mt-3 btn-bubble-clear'>Clear <i class=\"fas fa-eraser\"></i></button></div>",labelWithColumText="<label>".concat($dtHeadColumText,":</label>");if($evTarget.html().match(/^\s*\d[\d,\.]*\s*$/))$bubbleMainDiv.html($("<div>",{class:bubbleMainClass,html:"".concat(labelWithColumText,"<input type='number' class='text-left form-control' value='").concat($evTarget.text(),"'> ").concat(btnUpadteWithWrapper)}));else if("'".concat($dtHeadColumText.toLowerCase(),"'").includes("date"))$bubbleMainDiv.html($("<div>",{class:bubbleMainClass,html:"".concat(labelWithColumText,"<input type='date' class='text-left form-control' value='").concat($evTarget.text(),"'> ").concat(btnUpadteWithWrapper)}));else if("'".concat($dtHeadColumText.toLowerCase(),"'").includes("name")&&$evTarget.text().split(" ").length>=1){for(var $divToMainClass=$("<div class='".concat(bubbleMainClass,"'></div>")),_i10=0;_i10<$evTarget.text().split(" ").length;_i10++)$($bubbleMainDiv.html($divToMainClass.append("<div class='d-flex flex-column wrapper-bubble-label-input'><label class='mt-2 mb-1'>".concat(inWords(_i10)," ").concat($dtHeadColumText,":</label><div class='d-flex'><input type='text' class='text-left form-control' value='").concat($evTarget.text().split(" ").filter(function(i){return i}).splice(_i10,1).toString(),"'><span class='ml-2 wrapper-bubble-input-delete d-flex'><i class='fas fa-plus-circle blue-text bubble-input-add-icon mt-2 mr-1'></i><i class='fas fa-minus-circle red-text bubble-input-delete-icon mt-2'></i></span></div></div>")))).appendTo(btnUpadteWithWrapper),$(".bubble-inside").append(btnUpadteWithWrapper);$bubbleMainDiv.find("label:last").html("Last ".concat($dtHeadColumText,":")),$bubbleMainDiv.find("label:first").html("First ".concat($dtHeadColumText,":")),$divToMainClass.append(btnUpadteWithWrapper)}else $bubbleMainDiv.html($("<div>",{class:bubbleMainClass,html:"<label>".concat($dtHeadColumText,":</label><input type='text' class='text-left form-control' value='").concat($evTarget.text(),"'> ").concat(btnUpadteWithWrapper)}));$('<div class ="bubble-bg"></div>').appendTo("body"),$('<div class ="bubble-arrow"></div>').prependTo($wrapperEditor),$('<span class="text-right position-absolute bubble-span-close" style="top:-8px; right:-8px;"><i class="fas fa-times-circle"></i></span>').prependTo($('<button type="button" class="close text-right bubble-button-x"></button>').prependTo($bubbleMainDiv.prependTo($wrapperEditor)));var $wrapperBtnBubbleUpdate=$(".wrapper-btn-bubble-update"),$wrapperBubbleInputDelete=$(".wrapper-bubble-input-delete"),$bubbleBg=$(".bubble-bg"),$bubbleMain=$(".bubble-main"),$bubbleArrow=$(".bubble-arrow");$evTarget.addClass("tr-color-selected"),$bubbleMain.css({top:ev.pageY-options.bubblePositionX,left:ev.pageX-options.bubblePositionY}),$document.find(".bubble-bg").length>1&&$bubbleBg.not(":last").remove(),$wrapperEditor.find(".bubble-arrow").length>1&&$bubbleArrow.not(":last").remove();var removeBubbleDarkBg=function removeBubbleDarkBg(e){return $(e.target).remove()},removeArrowAndMain=function removeArrowAndMain(){return $(".bubble-main, .bubble-arrow").remove()},removeColorClassFromTd=function removeColorClassFromTd(){return $evTarget.removeClass("tr-color-selected")},takeDataTdIfHasColor=function takeDataTdIfHasColor(e){var $eTarget=$(e.target);if($evTarget.hasClass("tr-color-selected")&&"".concat($dtHeadColumText.toLowerCase()).includes("name")){for(var $new=[],$eventInputs=$eTarget.parent().parent().find("input"),_i11=0;_i11<$eventInputs.length;_i11++)$new.push($eventInputs.eq(_i11).val());$DataTable.cell($evTarget).data($new.toString().split(",").filter(function(i){return i}).join(" ")).draw()}else $evTarget.hasClass("tr-color-selected")&&$DataTable.cell($evTarget).data($eTarget.parent().prev().val()).draw()},findBubbleBgAddRemove=function findBubbleBgAddRemove(){return $document.find(".bubble-bg").remove()},btnUpdateRemoveArrowMain=function btnUpdateRemoveArrowMain(){return removeArrowAndMain()},removeColorFromTr=function removeColorFromTr(){return removeColorClassFromTd()},bubbleSpanRemove=function bubbleSpanRemove(){removeArrowAndMain(),removeColorClassFromTd(),findBubbleBgAddRemove()},btnClearInputs=function btnClearInputs(e){return $(e.target).parents().eq(1).find("input").val("")},keyupEvents=function keyupEvents(e){27===e.keyCode&&(removeArrowAndMain(),removeColorClassFromTd(),findBubbleBgAddRemove()),13===e.keyCode&&$wrapperEditor.find(".btn-bubble-update").click()},deleteClosestInputLabel=function deleteClosestInputLabel(e){$wrapperEditor.find(".bubble-inside input").length>1&&$(e.target).closest(".wrapper-bubble-label-input").remove(),$wrapperEditor.find(".wrapper-bubble-input-delete").first().children().length<2&&$wrapperEditor.find(".wrapper-bubble-input-delete").first().prepend("<i class='fas fa-plus-circle blue-text bubble-input-add-icon mt-2 mr-1'></i>")},addNewInput=function addNewInput(e){var $eTarget=$(e.target),$clondeDiv=$eTarget.parents().eq(2).clone(!0);$clondeDiv.insertAfter($eTarget.parents().eq(2)),$clondeDiv.addClass("cloned"),$clondeDiv.find("label").html("New Input:"),$clondeDiv.find("input").val(""),$clondeDiv.find(".wrapper-bubble-input-delete .fa-plus-circle").remove()},bindTdEvents;(function bindTdEvents(){$bubbleBg.on("click",removeBubbleDarkBg),$bubbleBg.on("click",removeArrowAndMain),$bubbleBg.on("click",removeColorClassFromTd),$wrapperBtnBubbleUpdate.on("click",".btn-bubble-update",takeDataTdIfHasColor),$wrapperBtnBubbleUpdate.on("click",".btn-bubble-update",findBubbleBgAddRemove),$wrapperBtnBubbleUpdate.on("click",".btn-bubble-update",btnUpdateRemoveArrowMain),$wrapperBtnBubbleUpdate.on("click",".btn-bubble-update",removeColorFromTr),$wrapperBtnBubbleUpdate.on("click",".btn-bubble-clear",btnClearInputs),$(".bubble-button-x").on("click",".bubble-span-close",bubbleSpanRemove),$wrapperBubbleInputDelete.on("click",".bubble-input-add-icon",addNewInput),$wrapperBubbleInputDelete.on("click",".bubble-input-delete-icon",deleteClosestInputLabel),$document.on("keyup",keyupEvents)})()})):($this.not(".fas").html('<i class="fas fa-toggle-off ml-1"></i>'),$wrapperEditor.find("table tbody").addClass("disabled-table"),$wrapperEditor.find("table td").not("thead, tfoot").removeClass("bubble-td-cursor"))});var _addNewRows2=function _addNewRows2(){for(var $newRow=[],_i12=0;_i12<editorOptions.headerLength;_i12++)$newRow.push($wrapperEditor.find("table thead th").eq(_i12).val());$DataTable.row.add($newRow).draw(!0)},removeFirstTr=function removeFirstTr(){return $DataTable.row().remove().draw(!0)};$wrapperEditor.find(".buttons-wrapper").on("click",".removeFirstTr",removeFirstTr),$wrapperEditor.find(".buttons-wrapper").on("click",".addNewRow",_addNewRows2)}})}}(jQuery);
