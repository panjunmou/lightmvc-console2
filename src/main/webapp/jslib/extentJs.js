/**
 * 拓展cookie
 * @param key
 * @param value
 * @param options
 * @returns {*}
 */
$.cookie = function (key, value, options) {
    if (arguments.length > 1 && (value === null || typeof value !== "object")) {
        options = $.extend({}, options);
        if (value === null) {
            options.expires = -1;
        }
        if (typeof options.expires === 'number') {
            var days = options.expires;
            var t = options.expires = new Date();
            t.setDate(t.getDate() + days);
        }
        document.cookie =
            [
                encodeURIComponent(key),
                '=',
                options.raw ? String(value) : encodeURIComponent(String(value)),
                options.expires ? '; expires=' + options.expires.toUTCString() : '',
                options.path ? '; path=' + options.path : '',
                options.domain ? '; domain=' + options.domain : '',
                options.secure ? '; secure' : ''
            ].join('');
        return document.cookie;
    }
    options = value || {};
    var result;
    var decode = options.raw ? function (s) {
        return s;
    } : decodeURIComponent;
    return (result = new RegExp('(?:^|; )' + encodeURIComponent(key) + '=([^;]*)').exec(document.cookie)) ? decode(result[1]) : null;
};
/**
 * 关闭进度条
 */
function progressClose() {
    $(".datagrid-mask").remove();
    $(".datagrid-mask-msg").remove();
}
/**
 * 创建一个模式化的dialog
 * @param optinos
 */
$.modalDialog = function (optinos) {
    if ($.modalDialog.handler == undefined) {
        var opts = $.extend({
            modal: true,
            onClose: function () {
                $.modalDialog.handler = undefined;
                $(this).dialog('destroy');
            }
        }, optinos);
        $.modalDialog.handler = $("<div/>").dialog(opts);
    }
};

/**
 * 加载进度条
 * @param message
 */
function progressLoad(message) {
    if (message == "" || message == null || message == undefined) {
        message = "正在处理,请稍后。。。";
    }
    $("<div class=\"datagrid-mask\" style=\"position:absolute;z-index: 9999;\"></div>").css({
        display: "block",
        width: "100%",
        height: $(window).height()
    }).appendTo("body");
    $("<div class=\"datagrid-mask-msg\" style=\"position:absolute;z-index: 9999;\"></div>").html(message).appendTo("body").css({
        display: "block",
        left: ($(document.body).outerWidth(true) - 190) / 2,
        top: ($(window).height() - 45) / 2
    });
}

/**
 * 格式化字符串
 * $.formatString('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 * @param str
 * @returns 格式化后的字符串
 */
$.formatString = function (str) {
    for (var i = 0; i < arguments.length - 1; i++) {
        str = str.replace("{" + i + "}", arguments[i + 1]);
    }
    return str;
};

var treeLoadFilter = function (data) {
    var opt = $(this).data().tree.options;
    var idField, textField;
    idField = opt.idField || 'id';
    textField = opt.textField || 'text';
    var treeData = [];
    for (var i = 0;i < data.length;i++) {
        data[i]['id'] = data[i][idField];
        data[i]['text'] = data[i][textField];
        treeData.push(data[i]);
    }
    return treeData;
};
/**
 * 重写tree的Filter
 * 增加idField,textField字段
 * 当定义这两个字段的时候,返回相应的值
 * 默认为id,text
 * @param data
 * @returns {Array}
 */
$.fn.tree.defaults.loadFilter = treeLoadFilter;
$.fn.combotree.defaults.loadFilter = treeLoadFilter;

