/*
 * Copyright 2009-2020 by Justin T. Sampson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.remarker;

import org.remarker.dom.*;

@SuppressWarnings("unused")
public final class Html
{
    private Html()
    {
        // to prevent instantiation
    }

    public static Element noHtml()
    {
        return asHtml();
    }

    public static Element asHtml(Object... contents)
    {
        return new Element(null, true, false, contents);
    }

    private static Element element(String name, Object[] contents)
    {
        ElementDefinition definition = SpecificationParser.ELEMENTS.get(name);
        return new Element(definition.uppercase, definition.inline, definition.empty, contents);
    }

    private static Attribute attribute(String name, String value)
    {
        return Attribute.quotedString(name, value);
    }

    private static Attribute attribute(String name, Boolean value)
    {
        return Attribute.traditionalBoolean(name, value);
    }

    private static Attribute attribute(String name, Integer value)
    {
        return Attribute.quotedString(name, value);
    }

    // BEGIN GENERATED CODE

    public static Element A(Object... contents)
    {
        return element("a", contents);
    }

    public static Element ABBR(Object... contents)
    {
        return element("abbr", contents);
    }

    public static Element ACRONYM(Object... contents)
    {
        return element("acronym", contents);
    }

    public static Element ADDRESS(Object... contents)
    {
        return element("address", contents);
    }

    public static Element AREA(Object... contents)
    {
        return element("area", contents);
    }

    public static Element B(Object... contents)
    {
        return element("b", contents);
    }

    public static Element BASE(Object... contents)
    {
        return element("base", contents);
    }

    public static Element BDO(Object... contents)
    {
        return element("bdo", contents);
    }

    public static Element BIG(Object... contents)
    {
        return element("big", contents);
    }

    public static Element BLOCKQUOTE(Object... contents)
    {
        return element("blockquote", contents);
    }

    public static Element BODY(Object... contents)
    {
        return element("body", contents);
    }

    public static Element BR(Object... contents)
    {
        return element("br", contents);
    }

    public static Element BUTTON(Object... contents)
    {
        return element("button", contents);
    }

    public static Element CAPTION(Object... contents)
    {
        return element("caption", contents);
    }

    public static Element CITE(Object... contents)
    {
        return element("cite", contents);
    }

    public static Element CODE(Object... contents)
    {
        return element("code", contents);
    }

    public static Element COL(Object... contents)
    {
        return element("col", contents);
    }

    public static Element COLGROUP(Object... contents)
    {
        return element("colgroup", contents);
    }

    public static Element DD(Object... contents)
    {
        return element("dd", contents);
    }

    public static Element DEL(Object... contents)
    {
        return element("del", contents);
    }

    public static Element DFN(Object... contents)
    {
        return element("dfn", contents);
    }

    public static Element DIV(Object... contents)
    {
        return element("div", contents);
    }

    public static Element DL(Object... contents)
    {
        return element("dl", contents);
    }

    public static Element DT(Object... contents)
    {
        return element("dt", contents);
    }

    public static Element EM(Object... contents)
    {
        return element("em", contents);
    }

    public static Element FIELDSET(Object... contents)
    {
        return element("fieldset", contents);
    }

    public static Element FORM(Object... contents)
    {
        return element("form", contents);
    }

    public static Element H1(Object... contents)
    {
        return element("h1", contents);
    }

    public static Element H2(Object... contents)
    {
        return element("h2", contents);
    }

    public static Element H3(Object... contents)
    {
        return element("h3", contents);
    }

    public static Element H4(Object... contents)
    {
        return element("h4", contents);
    }

    public static Element H5(Object... contents)
    {
        return element("h5", contents);
    }

    public static Element H6(Object... contents)
    {
        return element("h6", contents);
    }

    public static Element HEAD(Object... contents)
    {
        return element("head", contents);
    }

    public static Element HR(Object... contents)
    {
        return element("hr", contents);
    }

    public static Element HTML(Object... contents)
    {
        return element("html", contents);
    }

    public static Element I(Object... contents)
    {
        return element("i", contents);
    }

    public static Element IMG(Object... contents)
    {
        return element("img", contents);
    }

    public static Element INPUT(Object... contents)
    {
        return element("input", contents);
    }

    public static Element INS(Object... contents)
    {
        return element("ins", contents);
    }

    public static Element KBD(Object... contents)
    {
        return element("kbd", contents);
    }

    public static Element LABEL(Object... contents)
    {
        return element("label", contents);
    }

    public static Element LEGEND(Object... contents)
    {
        return element("legend", contents);
    }

    public static Element LI(Object... contents)
    {
        return element("li", contents);
    }

    public static Element LINK(Object... contents)
    {
        return element("link", contents);
    }

    public static Element MAP(Object... contents)
    {
        return element("map", contents);
    }

    public static Element META(Object... contents)
    {
        return element("meta", contents);
    }

    public static Element NOSCRIPT(Object... contents)
    {
        return element("noscript", contents);
    }

    public static Element OBJECT(Object... contents)
    {
        return element("object", contents);
    }

    public static Element OL(Object... contents)
    {
        return element("ol", contents);
    }

    public static Element OPTGROUP(Object... contents)
    {
        return element("optgroup", contents);
    }

    public static Element OPTION(Object... contents)
    {
        return element("option", contents);
    }

    public static Element P(Object... contents)
    {
        return element("p", contents);
    }

    public static Element PARAM(Object... contents)
    {
        return element("param", contents);
    }

    public static Element PRE(Object... contents)
    {
        return element("pre", contents);
    }

    public static Element Q(Object... contents)
    {
        return element("q", contents);
    }

    public static Element SAMP(Object... contents)
    {
        return element("samp", contents);
    }

    public static Element SCRIPT(Object... contents)
    {
        return element("script", contents);
    }

    public static Element SELECT(Object... contents)
    {
        return element("select", contents);
    }

    public static Element SMALL(Object... contents)
    {
        return element("small", contents);
    }

    public static Element SPAN(Object... contents)
    {
        return element("span", contents);
    }

    public static Element STRONG(Object... contents)
    {
        return element("strong", contents);
    }

    public static Element STYLE(Object... contents)
    {
        return element("style", contents);
    }

    public static Element SUB(Object... contents)
    {
        return element("sub", contents);
    }

    public static Element SUP(Object... contents)
    {
        return element("sup", contents);
    }

    public static Element TABLE(Object... contents)
    {
        return element("table", contents);
    }

    public static Element TBODY(Object... contents)
    {
        return element("tbody", contents);
    }

    public static Element TD(Object... contents)
    {
        return element("td", contents);
    }

    public static Element TEXTAREA(Object... contents)
    {
        return element("textarea", contents);
    }

    public static Element TFOOT(Object... contents)
    {
        return element("tfoot", contents);
    }

    public static Element TH(Object... contents)
    {
        return element("th", contents);
    }

    public static Element THEAD(Object... contents)
    {
        return element("thead", contents);
    }

    public static Element TITLE(Object... contents)
    {
        return element("title", contents);
    }

    public static Element TR(Object... contents)
    {
        return element("tr", contents);
    }

    public static Element TT(Object... contents)
    {
        return element("tt", contents);
    }

    public static Element UL(Object... contents)
    {
        return element("ul", contents);
    }

    public static Element VAR(Object... contents)
    {
        return element("var", contents);
    }

    public static Attribute Abbr(String value)
    {
        return attribute("abbr", value);
    }

    public static Attribute Accept(String value)
    {
        return attribute("accept", value);
    }

    public static Attribute AcceptCharset(String value)
    {
        return attribute("accept-charset", value);
    }

    public static Attribute Accesskey(String value)
    {
        return attribute("accesskey", value);
    }

    public static Attribute Action(String value)
    {
        return attribute("action", value);
    }

    public static Attribute Align(String value)
    {
        return attribute("align", value);
    }

    public static Attribute Alt(String value)
    {
        return attribute("alt", value);
    }

    public static Attribute Archive(String value)
    {
        return attribute("archive", value);
    }

    public static Attribute Axis(String value)
    {
        return attribute("axis", value);
    }

    public static Attribute Border(String value)
    {
        return attribute("border", value);
    }

    public static Attribute Cellpadding(String value)
    {
        return attribute("cellpadding", value);
    }

    public static Attribute Cellspacing(String value)
    {
        return attribute("cellspacing", value);
    }

    public static Attribute Char(String value)
    {
        return attribute("char", value);
    }

    public static Attribute Charoff(String value)
    {
        return attribute("charoff", value);
    }

    public static Attribute Charset(String value)
    {
        return attribute("charset", value);
    }

    public static Attribute Checked(String value)
    {
        return attribute("checked", value);
    }

    public static Attribute Checked(Boolean value)
    {
        return attribute("checked", value);
    }

    public static Attribute Cite(String value)
    {
        return attribute("cite", value);
    }

    public static Attribute Class(String value)
    {
        return attribute("class", value);
    }

    public static Attribute Classid(String value)
    {
        return attribute("classid", value);
    }

    public static Attribute Codebase(String value)
    {
        return attribute("codebase", value);
    }

    public static Attribute Codetype(String value)
    {
        return attribute("codetype", value);
    }

    public static Attribute Cols(String value)
    {
        return attribute("cols", value);
    }

    public static Attribute Cols(Integer value)
    {
        return attribute("cols", value);
    }

    public static Attribute Colspan(String value)
    {
        return attribute("colspan", value);
    }

    public static Attribute Colspan(Integer value)
    {
        return attribute("colspan", value);
    }

    public static Attribute Content(String value)
    {
        return attribute("content", value);
    }

    public static Attribute Coords(String value)
    {
        return attribute("coords", value);
    }

    public static Attribute Data(String value)
    {
        return attribute("data", value);
    }

    public static Attribute Datetime(String value)
    {
        return attribute("datetime", value);
    }

    public static Attribute Declare(String value)
    {
        return attribute("declare", value);
    }

    public static Attribute Declare(Boolean value)
    {
        return attribute("declare", value);
    }

    public static Attribute Defer(String value)
    {
        return attribute("defer", value);
    }

    public static Attribute Defer(Boolean value)
    {
        return attribute("defer", value);
    }

    public static Attribute Dir(String value)
    {
        return attribute("dir", value);
    }

    public static Attribute Disabled(String value)
    {
        return attribute("disabled", value);
    }

    public static Attribute Disabled(Boolean value)
    {
        return attribute("disabled", value);
    }

    public static Attribute Enctype(String value)
    {
        return attribute("enctype", value);
    }

    public static Attribute For(String value)
    {
        return attribute("for", value);
    }

    public static Attribute Frame(String value)
    {
        return attribute("frame", value);
    }

    public static Attribute Headers(String value)
    {
        return attribute("headers", value);
    }

    public static Attribute Height(String value)
    {
        return attribute("height", value);
    }

    public static Attribute Href(String value)
    {
        return attribute("href", value);
    }

    public static Attribute Hreflang(String value)
    {
        return attribute("hreflang", value);
    }

    public static Attribute HttpEquiv(String value)
    {
        return attribute("http-equiv", value);
    }

    public static Attribute Id(String value)
    {
        return attribute("id", value);
    }

    public static Attribute Ismap(String value)
    {
        return attribute("ismap", value);
    }

    public static Attribute Ismap(Boolean value)
    {
        return attribute("ismap", value);
    }

    public static Attribute Label(String value)
    {
        return attribute("label", value);
    }

    public static Attribute Lang(String value)
    {
        return attribute("lang", value);
    }

    public static Attribute Longdesc(String value)
    {
        return attribute("longdesc", value);
    }

    public static Attribute Maxlength(String value)
    {
        return attribute("maxlength", value);
    }

    public static Attribute Maxlength(Integer value)
    {
        return attribute("maxlength", value);
    }

    public static Attribute Media(String value)
    {
        return attribute("media", value);
    }

    public static Attribute Method(String value)
    {
        return attribute("method", value);
    }

    public static Attribute Multiple(String value)
    {
        return attribute("multiple", value);
    }

    public static Attribute Multiple(Boolean value)
    {
        return attribute("multiple", value);
    }

    public static Attribute Name(String value)
    {
        return attribute("name", value);
    }

    public static Attribute Nohref(String value)
    {
        return attribute("nohref", value);
    }

    public static Attribute Nohref(Boolean value)
    {
        return attribute("nohref", value);
    }

    public static Attribute Onblur(String value)
    {
        return attribute("onblur", value);
    }

    public static Attribute Onchange(String value)
    {
        return attribute("onchange", value);
    }

    public static Attribute Onclick(String value)
    {
        return attribute("onclick", value);
    }

    public static Attribute Ondblclick(String value)
    {
        return attribute("ondblclick", value);
    }

    public static Attribute Onfocus(String value)
    {
        return attribute("onfocus", value);
    }

    public static Attribute Onkeydown(String value)
    {
        return attribute("onkeydown", value);
    }

    public static Attribute Onkeypress(String value)
    {
        return attribute("onkeypress", value);
    }

    public static Attribute Onkeyup(String value)
    {
        return attribute("onkeyup", value);
    }

    public static Attribute Onload(String value)
    {
        return attribute("onload", value);
    }

    public static Attribute Onmousedown(String value)
    {
        return attribute("onmousedown", value);
    }

    public static Attribute Onmousemove(String value)
    {
        return attribute("onmousemove", value);
    }

    public static Attribute Onmouseout(String value)
    {
        return attribute("onmouseout", value);
    }

    public static Attribute Onmouseover(String value)
    {
        return attribute("onmouseover", value);
    }

    public static Attribute Onmouseup(String value)
    {
        return attribute("onmouseup", value);
    }

    public static Attribute Onreset(String value)
    {
        return attribute("onreset", value);
    }

    public static Attribute Onselect(String value)
    {
        return attribute("onselect", value);
    }

    public static Attribute Onsubmit(String value)
    {
        return attribute("onsubmit", value);
    }

    public static Attribute Onunload(String value)
    {
        return attribute("onunload", value);
    }

    public static Attribute Profile(String value)
    {
        return attribute("profile", value);
    }

    public static Attribute Readonly(String value)
    {
        return attribute("readonly", value);
    }

    public static Attribute Readonly(Boolean value)
    {
        return attribute("readonly", value);
    }

    public static Attribute Rel(String value)
    {
        return attribute("rel", value);
    }

    public static Attribute Rev(String value)
    {
        return attribute("rev", value);
    }

    public static Attribute Rows(String value)
    {
        return attribute("rows", value);
    }

    public static Attribute Rows(Integer value)
    {
        return attribute("rows", value);
    }

    public static Attribute Rowspan(String value)
    {
        return attribute("rowspan", value);
    }

    public static Attribute Rowspan(Integer value)
    {
        return attribute("rowspan", value);
    }

    public static Attribute Rules(String value)
    {
        return attribute("rules", value);
    }

    public static Attribute Scheme(String value)
    {
        return attribute("scheme", value);
    }

    public static Attribute Scope(String value)
    {
        return attribute("scope", value);
    }

    public static Attribute Selected(String value)
    {
        return attribute("selected", value);
    }

    public static Attribute Selected(Boolean value)
    {
        return attribute("selected", value);
    }

    public static Attribute Shape(String value)
    {
        return attribute("shape", value);
    }

    public static Attribute Size(String value)
    {
        return attribute("size", value);
    }

    public static Attribute Size(Integer value)
    {
        return attribute("size", value);
    }

    public static Attribute Span(String value)
    {
        return attribute("span", value);
    }

    public static Attribute Span(Integer value)
    {
        return attribute("span", value);
    }

    public static Attribute Src(String value)
    {
        return attribute("src", value);
    }

    public static Attribute Standby(String value)
    {
        return attribute("standby", value);
    }

    public static Attribute Style(String value)
    {
        return attribute("style", value);
    }

    public static Attribute Summary(String value)
    {
        return attribute("summary", value);
    }

    public static Attribute Tabindex(String value)
    {
        return attribute("tabindex", value);
    }

    public static Attribute Tabindex(Integer value)
    {
        return attribute("tabindex", value);
    }

    public static Attribute Title(String value)
    {
        return attribute("title", value);
    }

    public static Attribute Type(String value)
    {
        return attribute("type", value);
    }

    public static Attribute Usemap(String value)
    {
        return attribute("usemap", value);
    }

    public static Attribute Valign(String value)
    {
        return attribute("valign", value);
    }

    public static Attribute Value(String value)
    {
        return attribute("value", value);
    }

    public static Attribute Valuetype(String value)
    {
        return attribute("valuetype", value);
    }

    public static Attribute Width(String value)
    {
        return attribute("width", value);
    }

    public static final String _AElig = String.valueOf((char) 198);
    public static final String _Aacute = String.valueOf((char) 193);
    public static final String _Acirc = String.valueOf((char) 194);
    public static final String _Agrave = String.valueOf((char) 192);
    public static final String _Alpha = String.valueOf((char) 913);
    public static final String _Aring = String.valueOf((char) 197);
    public static final String _Atilde = String.valueOf((char) 195);
    public static final String _Auml = String.valueOf((char) 196);
    public static final String _Beta = String.valueOf((char) 914);
    public static final String _Ccedil = String.valueOf((char) 199);
    public static final String _Chi = String.valueOf((char) 935);
    public static final String _Dagger = String.valueOf((char) 8225);
    public static final String _Delta = String.valueOf((char) 916);
    public static final String _ETH = String.valueOf((char) 208);
    public static final String _Eacute = String.valueOf((char) 201);
    public static final String _Ecirc = String.valueOf((char) 202);
    public static final String _Egrave = String.valueOf((char) 200);
    public static final String _Epsilon = String.valueOf((char) 917);
    public static final String _Eta = String.valueOf((char) 919);
    public static final String _Euml = String.valueOf((char) 203);
    public static final String _Gamma = String.valueOf((char) 915);
    public static final String _Iacute = String.valueOf((char) 205);
    public static final String _Icirc = String.valueOf((char) 206);
    public static final String _Igrave = String.valueOf((char) 204);
    public static final String _Iota = String.valueOf((char) 921);
    public static final String _Iuml = String.valueOf((char) 207);
    public static final String _Kappa = String.valueOf((char) 922);
    public static final String _Lambda = String.valueOf((char) 923);
    public static final String _Mu = String.valueOf((char) 924);
    public static final String _Ntilde = String.valueOf((char) 209);
    public static final String _Nu = String.valueOf((char) 925);
    public static final String _OElig = String.valueOf((char) 338);
    public static final String _Oacute = String.valueOf((char) 211);
    public static final String _Ocirc = String.valueOf((char) 212);
    public static final String _Ograve = String.valueOf((char) 210);
    public static final String _Omega = String.valueOf((char) 937);
    public static final String _Omicron = String.valueOf((char) 927);
    public static final String _Oslash = String.valueOf((char) 216);
    public static final String _Otilde = String.valueOf((char) 213);
    public static final String _Ouml = String.valueOf((char) 214);
    public static final String _Phi = String.valueOf((char) 934);
    public static final String _Pi = String.valueOf((char) 928);
    public static final String _Prime = String.valueOf((char) 8243);
    public static final String _Psi = String.valueOf((char) 936);
    public static final String _Rho = String.valueOf((char) 929);
    public static final String _Scaron = String.valueOf((char) 352);
    public static final String _Sigma = String.valueOf((char) 931);
    public static final String _THORN = String.valueOf((char) 222);
    public static final String _Tau = String.valueOf((char) 932);
    public static final String _Theta = String.valueOf((char) 920);
    public static final String _Uacute = String.valueOf((char) 218);
    public static final String _Ucirc = String.valueOf((char) 219);
    public static final String _Ugrave = String.valueOf((char) 217);
    public static final String _Upsilon = String.valueOf((char) 933);
    public static final String _Uuml = String.valueOf((char) 220);
    public static final String _Xi = String.valueOf((char) 926);
    public static final String _Yacute = String.valueOf((char) 221);
    public static final String _Yuml = String.valueOf((char) 376);
    public static final String _Zeta = String.valueOf((char) 918);
    public static final String _aacute = String.valueOf((char) 225);
    public static final String _acirc = String.valueOf((char) 226);
    public static final String _acute = String.valueOf((char) 180);
    public static final String _aelig = String.valueOf((char) 230);
    public static final String _agrave = String.valueOf((char) 224);
    public static final String _alefsym = String.valueOf((char) 8501);
    public static final String _alpha = String.valueOf((char) 945);
    public static final String _amp = String.valueOf((char) 38);
    public static final String _and = String.valueOf((char) 8743);
    public static final String _ang = String.valueOf((char) 8736);
    public static final String _apos = String.valueOf((char) 39);
    public static final String _aring = String.valueOf((char) 229);
    public static final String _asymp = String.valueOf((char) 8776);
    public static final String _atilde = String.valueOf((char) 227);
    public static final String _auml = String.valueOf((char) 228);
    public static final String _bdquo = String.valueOf((char) 8222);
    public static final String _beta = String.valueOf((char) 946);
    public static final String _brvbar = String.valueOf((char) 166);
    public static final String _bull = String.valueOf((char) 8226);
    public static final String _cap = String.valueOf((char) 8745);
    public static final String _ccedil = String.valueOf((char) 231);
    public static final String _cedil = String.valueOf((char) 184);
    public static final String _cent = String.valueOf((char) 162);
    public static final String _chi = String.valueOf((char) 967);
    public static final String _circ = String.valueOf((char) 710);
    public static final String _clubs = String.valueOf((char) 9827);
    public static final String _cong = String.valueOf((char) 8773);
    public static final String _copy = String.valueOf((char) 169);
    public static final String _crarr = String.valueOf((char) 8629);
    public static final String _cup = String.valueOf((char) 8746);
    public static final String _curren = String.valueOf((char) 164);
    public static final String _dArr = String.valueOf((char) 8659);
    public static final String _dagger = String.valueOf((char) 8224);
    public static final String _darr = String.valueOf((char) 8595);
    public static final String _deg = String.valueOf((char) 176);
    public static final String _delta = String.valueOf((char) 948);
    public static final String _diams = String.valueOf((char) 9830);
    public static final String _divide = String.valueOf((char) 247);
    public static final String _eacute = String.valueOf((char) 233);
    public static final String _ecirc = String.valueOf((char) 234);
    public static final String _egrave = String.valueOf((char) 232);
    public static final String _empty = String.valueOf((char) 8709);
    public static final String _emsp = String.valueOf((char) 8195);
    public static final String _emsp_2 = _emsp + _emsp;
    public static final String _emsp_3 = _emsp + _emsp + _emsp;
    public static final String _emsp_4 = _emsp + _emsp + _emsp + _emsp;
    public static final String _emsp_5 = _emsp + _emsp + _emsp + _emsp + _emsp;
    public static final String _emsp_6 = _emsp + _emsp + _emsp + _emsp + _emsp + _emsp;
    public static final String _emsp_7 = _emsp + _emsp + _emsp + _emsp + _emsp + _emsp + _emsp;
    public static final String _emsp_8 = _emsp + _emsp + _emsp + _emsp + _emsp + _emsp + _emsp + _emsp;
    public static final String _ensp = String.valueOf((char) 8194);
    public static final String _ensp_2 = _ensp + _ensp;
    public static final String _ensp_3 = _ensp + _ensp + _ensp;
    public static final String _ensp_4 = _ensp + _ensp + _ensp + _ensp;
    public static final String _ensp_5 = _ensp + _ensp + _ensp + _ensp + _ensp;
    public static final String _ensp_6 = _ensp + _ensp + _ensp + _ensp + _ensp + _ensp;
    public static final String _ensp_7 = _ensp + _ensp + _ensp + _ensp + _ensp + _ensp + _ensp;
    public static final String _ensp_8 = _ensp + _ensp + _ensp + _ensp + _ensp + _ensp + _ensp + _ensp;
    public static final String _epsilon = String.valueOf((char) 949);
    public static final String _equiv = String.valueOf((char) 8801);
    public static final String _eta = String.valueOf((char) 951);
    public static final String _eth = String.valueOf((char) 240);
    public static final String _euml = String.valueOf((char) 235);
    public static final String _euro = String.valueOf((char) 8364);
    public static final String _exist = String.valueOf((char) 8707);
    public static final String _fnof = String.valueOf((char) 402);
    public static final String _forall = String.valueOf((char) 8704);
    public static final String _frac12 = String.valueOf((char) 189);
    public static final String _frac14 = String.valueOf((char) 188);
    public static final String _frac34 = String.valueOf((char) 190);
    public static final String _frasl = String.valueOf((char) 8260);
    public static final String _gamma = String.valueOf((char) 947);
    public static final String _ge = String.valueOf((char) 8805);
    public static final String _gt = String.valueOf((char) 62);
    public static final String _hArr = String.valueOf((char) 8660);
    public static final String _harr = String.valueOf((char) 8596);
    public static final String _hearts = String.valueOf((char) 9829);
    public static final String _hellip = String.valueOf((char) 8230);
    public static final String _iacute = String.valueOf((char) 237);
    public static final String _icirc = String.valueOf((char) 238);
    public static final String _iexcl = String.valueOf((char) 161);
    public static final String _igrave = String.valueOf((char) 236);
    public static final String _image = String.valueOf((char) 8465);
    public static final String _infin = String.valueOf((char) 8734);
    public static final String _int = String.valueOf((char) 8747);
    public static final String _iota = String.valueOf((char) 953);
    public static final String _iquest = String.valueOf((char) 191);
    public static final String _isin = String.valueOf((char) 8712);
    public static final String _iuml = String.valueOf((char) 239);
    public static final String _kappa = String.valueOf((char) 954);
    public static final String _lArr = String.valueOf((char) 8656);
    public static final String _lambda = String.valueOf((char) 955);
    public static final String _lang = String.valueOf((char) 9001);
    public static final String _laquo = String.valueOf((char) 171);
    public static final String _larr = String.valueOf((char) 8592);
    public static final String _lceil = String.valueOf((char) 8968);
    public static final String _ldquo = String.valueOf((char) 8220);
    public static final String _le = String.valueOf((char) 8804);
    public static final String _lfloor = String.valueOf((char) 8970);
    public static final String _lowast = String.valueOf((char) 8727);
    public static final String _loz = String.valueOf((char) 9674);
    public static final String _lrm = String.valueOf((char) 8206);
    public static final String _lsaquo = String.valueOf((char) 8249);
    public static final String _lsquo = String.valueOf((char) 8216);
    public static final String _lt = String.valueOf((char) 60);
    public static final String _macr = String.valueOf((char) 175);
    public static final String _mdash = String.valueOf((char) 8212);
    public static final String _micro = String.valueOf((char) 181);
    public static final String _middot = String.valueOf((char) 183);
    public static final String _minus = String.valueOf((char) 8722);
    public static final String _mu = String.valueOf((char) 956);
    public static final String _nabla = String.valueOf((char) 8711);
    public static final String _nbsp = String.valueOf((char) 160);
    public static final String _nbsp_2 = _nbsp + _nbsp;
    public static final String _nbsp_3 = _nbsp + _nbsp + _nbsp;
    public static final String _nbsp_4 = _nbsp + _nbsp + _nbsp + _nbsp;
    public static final String _nbsp_5 = _nbsp + _nbsp + _nbsp + _nbsp + _nbsp;
    public static final String _nbsp_6 = _nbsp + _nbsp + _nbsp + _nbsp + _nbsp + _nbsp;
    public static final String _nbsp_7 = _nbsp + _nbsp + _nbsp + _nbsp + _nbsp + _nbsp + _nbsp;
    public static final String _nbsp_8 = _nbsp + _nbsp + _nbsp + _nbsp + _nbsp + _nbsp + _nbsp + _nbsp;
    public static final String _ndash = String.valueOf((char) 8211);
    public static final String _ne = String.valueOf((char) 8800);
    public static final String _ni = String.valueOf((char) 8715);
    public static final String _not = String.valueOf((char) 172);
    public static final String _notin = String.valueOf((char) 8713);
    public static final String _nsub = String.valueOf((char) 8836);
    public static final String _ntilde = String.valueOf((char) 241);
    public static final String _nu = String.valueOf((char) 957);
    public static final String _oacute = String.valueOf((char) 243);
    public static final String _ocirc = String.valueOf((char) 244);
    public static final String _oelig = String.valueOf((char) 339);
    public static final String _ograve = String.valueOf((char) 242);
    public static final String _oline = String.valueOf((char) 8254);
    public static final String _omega = String.valueOf((char) 969);
    public static final String _omicron = String.valueOf((char) 959);
    public static final String _oplus = String.valueOf((char) 8853);
    public static final String _or = String.valueOf((char) 8744);
    public static final String _ordf = String.valueOf((char) 170);
    public static final String _ordm = String.valueOf((char) 186);
    public static final String _oslash = String.valueOf((char) 248);
    public static final String _otilde = String.valueOf((char) 245);
    public static final String _otimes = String.valueOf((char) 8855);
    public static final String _ouml = String.valueOf((char) 246);
    public static final String _para = String.valueOf((char) 182);
    public static final String _part = String.valueOf((char) 8706);
    public static final String _permil = String.valueOf((char) 8240);
    public static final String _perp = String.valueOf((char) 8869);
    public static final String _phi = String.valueOf((char) 966);
    public static final String _pi = String.valueOf((char) 960);
    public static final String _piv = String.valueOf((char) 982);
    public static final String _plusmn = String.valueOf((char) 177);
    public static final String _pound = String.valueOf((char) 163);
    public static final String _prime = String.valueOf((char) 8242);
    public static final String _prod = String.valueOf((char) 8719);
    public static final String _prop = String.valueOf((char) 8733);
    public static final String _psi = String.valueOf((char) 968);
    public static final String _quot = String.valueOf((char) 34);
    public static final String _rArr = String.valueOf((char) 8658);
    public static final String _radic = String.valueOf((char) 8730);
    public static final String _rang = String.valueOf((char) 9002);
    public static final String _raquo = String.valueOf((char) 187);
    public static final String _rarr = String.valueOf((char) 8594);
    public static final String _rceil = String.valueOf((char) 8969);
    public static final String _rdquo = String.valueOf((char) 8221);
    public static final String _real = String.valueOf((char) 8476);
    public static final String _reg = String.valueOf((char) 174);
    public static final String _rfloor = String.valueOf((char) 8971);
    public static final String _rho = String.valueOf((char) 961);
    public static final String _rlm = String.valueOf((char) 8207);
    public static final String _rsaquo = String.valueOf((char) 8250);
    public static final String _rsquo = String.valueOf((char) 8217);
    public static final String _sbquo = String.valueOf((char) 8218);
    public static final String _scaron = String.valueOf((char) 353);
    public static final String _sdot = String.valueOf((char) 8901);
    public static final String _sect = String.valueOf((char) 167);
    public static final String _shy = String.valueOf((char) 173);
    public static final String _sigma = String.valueOf((char) 963);
    public static final String _sigmaf = String.valueOf((char) 962);
    public static final String _sim = String.valueOf((char) 8764);
    public static final String _spades = String.valueOf((char) 9824);
    public static final String _sub = String.valueOf((char) 8834);
    public static final String _sube = String.valueOf((char) 8838);
    public static final String _sum = String.valueOf((char) 8721);
    public static final String _sup = String.valueOf((char) 8835);
    public static final String _sup1 = String.valueOf((char) 185);
    public static final String _sup2 = String.valueOf((char) 178);
    public static final String _sup3 = String.valueOf((char) 179);
    public static final String _supe = String.valueOf((char) 8839);
    public static final String _szlig = String.valueOf((char) 223);
    public static final String _tau = String.valueOf((char) 964);
    public static final String _there4 = String.valueOf((char) 8756);
    public static final String _theta = String.valueOf((char) 952);
    public static final String _thetasym = String.valueOf((char) 977);
    public static final String _thinsp = String.valueOf((char) 8201);
    public static final String _thinsp_2 = _thinsp + _thinsp;
    public static final String _thinsp_3 = _thinsp + _thinsp + _thinsp;
    public static final String _thinsp_4 = _thinsp + _thinsp + _thinsp + _thinsp;
    public static final String _thinsp_5 = _thinsp + _thinsp + _thinsp + _thinsp + _thinsp;
    public static final String _thinsp_6 = _thinsp + _thinsp + _thinsp + _thinsp + _thinsp + _thinsp;
    public static final String _thinsp_7 = _thinsp + _thinsp + _thinsp + _thinsp + _thinsp + _thinsp + _thinsp;
    public static final String _thinsp_8 = _thinsp + _thinsp + _thinsp + _thinsp + _thinsp + _thinsp + _thinsp + _thinsp;
    public static final String _thorn = String.valueOf((char) 254);
    public static final String _tilde = String.valueOf((char) 732);
    public static final String _times = String.valueOf((char) 215);
    public static final String _trade = String.valueOf((char) 8482);
    public static final String _uArr = String.valueOf((char) 8657);
    public static final String _uacute = String.valueOf((char) 250);
    public static final String _uarr = String.valueOf((char) 8593);
    public static final String _ucirc = String.valueOf((char) 251);
    public static final String _ugrave = String.valueOf((char) 249);
    public static final String _uml = String.valueOf((char) 168);
    public static final String _upsih = String.valueOf((char) 978);
    public static final String _upsilon = String.valueOf((char) 965);
    public static final String _uuml = String.valueOf((char) 252);
    public static final String _weierp = String.valueOf((char) 8472);
    public static final String _xi = String.valueOf((char) 958);
    public static final String _yacute = String.valueOf((char) 253);
    public static final String _yen = String.valueOf((char) 165);
    public static final String _yuml = String.valueOf((char) 255);
    public static final String _zeta = String.valueOf((char) 950);
    public static final String _zwj = String.valueOf((char) 8205);
    public static final String _zwnj = String.valueOf((char) 8204);

    // END GENERATED CODE
}
