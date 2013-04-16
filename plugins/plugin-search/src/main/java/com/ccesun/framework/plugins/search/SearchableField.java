package com.ccesun.framework.plugins.search;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;

/**
 * <p>
 * 版权所有：长春易申软件有限公司
 * <p>
 * 侵权者将受到法律追究。
 * <p>
 * DERIVED FROM: NONE
 * <p>
 * PURPOSE:
 * <p>
 * DESCRIPTION:
 * <p>
 * UPDATE: mawm
 * <p>
 * DATE: 2013-4-15 下午5:14:51
 * <p>
 * HISTORY: 1.0
 * 
 * @version 1.0
 * @author mawm
 * 
 * <br>
 *         功能描述:<br>
 *         请把主键字段标记上,否则存储的数据无法定位具体是那条记录
 *         <ul>
 *         <li> TermQuery是最简单、也是最常用的Query。TermQuery可以理解成为“词条搜索”，
 *         在搜索引擎中最基本的搜索就是在索引中搜索某一词条，而TermQuery就是用来完成这项工作的。
 * 
 *         <code>query = new TermQuery(new Term("name","word1")); </code></li>
 * 
 *         <li>
 *         “与或”搜索—BooleanQuery,BooleanQuery也是实际开发过程中经常使用的一种Query。它其实是一个组合的Query，
 *         在使用时可以把各种Query对象添加进去并标明它们之间的逻辑关系
 *         。在本节中所讨论的所有查询类型都可以使用BooleanQuery综合起来。BooleanQuery本身来讲是一个布尔子句的容器
 *         ，它提供了专门的API方法往其中添加子句，并标明它们之间的关系，
 *         注意：BooleanQuery是可以嵌套的，一个BooleanQuery可以成为另一个BooleanQuery的条件子句。
 *         第1个参数的意思是当前所加入的查询子句是否必须满足
 *         ，第2个参数的意思是当前所加入的查询子句是否不需要满足。这样，当这两个参数分别选择true和false时，会有4种不同的组合。
 * 
 *         true ＆false：表明当前加入的子句是必须要满足的。
 * 
 *         false＆true：表明当前加入的子句是不可以被满足的。
 * 
 *         false＆false：表明当前加入的子句是可选的。
 * 
 *         true＆true：错误的情况。
 * 
 *         由前面的示例可以看出由于加入的两个子句都选用了true＆false的组合，因此它们两个都是需要被满足的，也就构成了实际上的“与”关系
 * 
 *         <pre>
 * 
 *         query1 = new TermQuery(new Term("name","word1")); query2 = new
 *         TermQuery(new Term("name","word2")); // 构造一个布尔查询 query = new
 *         BooleanQuery(); // 添加两个子查询 query.add(query1, true, false);
 *         query.add(query2, true, false);
 * 
 *         </pre>
 * 
 *         </li>
 * 
 *         <li>在某一范围内搜索—RangeQuery 有时用户会需要一种在一个范围内查找某个文档，比如查找某一时间段内的所有文档，此时，
 *         Lucene提供了一种名为RangeQuery的类来满足这种需求 。
 * 
 *         RangeQuery表示在某范围内的搜索条件，实现从一个开始词条到一个结束词条的搜索功能，在查询时“开始词条”和“结束词条”
 *         可以被包含在内也可以不被包含在内。它的具体用法如下：
 * 
 *         RangeQuery query = new RangeQuery(begin, end, included);
 * 
 *         在参数列表中，最后一个boolean值表示是否包含边界条件本身，即当其为TRUE时，表示包含边界值，用字符可以表示为“[begin TO
 *         end]”；当其为FALSE时，表示不包含边界值，用字符可以表示为“{begin TO end}”。
 * 
 * 
 * 
 *         <pre>
 * 
 *         //构造词条 Term beginTime = new Term("time","200001"); Term endTime = new
 *         Term("time","200005");
 * 
 * 
 *         //生成RangeQuery对象，初始化为null RangeQuery query = null;
 * 
 *         //构造RangeQuery对象，检索条件中不包含边界值 query = new RangeQuery(beginTime,
 *         endTime, false);
 * 
 *         <pre>
 * 
 * 
 * 
 *         </li>
 * 
 *         <li>使用前缀搜索—PrefixQuery
 *         PrefixQuery就是使用前缀来进行查找的。通常情况下，首先定义一个词条Term。该词条包含要查找的字段名以及关键字的前缀
 *         ，然后通过该词条构造一个PrefixQuery对象，就可以进行前缀查找了。
 * 
 *         <pre>
 * 
 *         //构造词条
 * 
 *         Term pre1 = new Term("name", "Da"); //生成PrefixQuery类型的对象，初始化为null
 *         PrefixQuery query = null; query = new PrefixQuery(pre1);
 * 
 *         </pre>
 * 
 *         </li>
 * 
 *         <li> 多关键字的搜索—PhraseQuery,除了普通的TermQuery外，Lucene还提供了一种Phrase查
 *         询的功能。用户在搜索引擎中进行搜索时
 *         ，常常查找的并非是一个简单的单词，很有可能是几个不同的关键字。这些关键字之间要么是紧密相联，成为一个精确的短
 *         语，要么是可能在这几个关键字之间还插有其他无关的关键字
 *         。此时，用户希望将它们找出来。不过很显然，从评分的角度看，这些关键字之间拥有与查找内容无关 短语所在的文档的分值一般会较低一些。
 * 
 *         PhraseQuery正是Lucene所提供的满足上述需求的一种Query对象。它的add方法可以让用户往其内部添加关键字，在添加完毕后，
 *         用户还可以通过setSlop()方法来设定一个称之为“坡度”的变量来确定关键字之间是否允许、允许多少个无关词汇的存在。
 *         对两个紧连的关键字来说无论将坡度设置为多少，Lucene总能找到它所在的文档，而对两个不紧连的关键字，如果坡度值小于它们之间无关词的数量，
 *         那么则无法找到。其实，当两个关键字之间的无关词数小于等于坡度值时，总是可以被找到。
 * 
 *         <pre>
 * 
 *         //构造词条
 * 
 *         Term word1 = new Term("content", "david"); Term word2 = new
 *         Term("content","mary"); Term word3 = new Term("content","smith");
 *         Term word4 = new Term("content","robert"); PhraseQuery query = null;
 * 
 *         // 第一种情况，两个词本身紧密相连，先设置坡度为0，再设置坡度为2 query = new PhraseQuery();
 *         query.add(word1); query.add(word2); //设置坡度 query.setSlop(0);
 *         //开始检索，并返回检索结果 hits = searcher.search(query);
 * 
 *         </pre>
 * 
 * 
 *         </li>
 * 
 *         <li> 使用短语缀搜索—PhrasePrefixQuery
 *         ,PhrasePrefixQuery与Phrase有些类似。在PhraseQuery中，如果用户想查找短语“david
 *         robert”，又想查找短语“mary
 *         robert”。那么，他就只能构建两个PhraseQuery，然后再使用BooleanQuery将它们作为其中的子句
 *         ，并使用“或”操作符来连接，这样就能达到需要的效果。PhrasePrefixQuery可以让用户很方便地实现这种需要。
 * 
 *         <pre>
 * 
 *         //构造词条
 * 
 *         Term word1 = new Term("content", "david");
 * 
 *         Term word2 = new Term("content", "mary");
 * 
 *         Term word3 = new Term("content", "smith");
 * 
 *         Term word4 = new Term("content", "robert");
 * 
 * 
 * 
 *         //用于保存检索结果
 * 
 *         Hits hits = null;
 * 
 *         //生成PhrasePrefixQuery对象，初始化为null
 * 
 *         PhrasePrefixQuery query = null;
 * 
 * 
 * 
 *         query = new PhrasePrefixQuery();
 * 
 *         // 加入可能的所有不确定的词
 * 
 *         query.add(new Term[]{word1, word2});
 * 
 *         // 加入确定的词
 * 
 *         query.add(word4);
 * 
 *         //设置坡度
 * 
 *         query.setSlop(2);
 * 
 *         //开始检索，并返回检索结果
 * 
 *         hits = searcher.search(query);
 * 
 *         </pre>
 * 
 *         </li>
 * 
 *         <li> 相近词语的搜索—FuzzyQuery: FuzzyQuery是一种模糊查询，它可以简单地识别两个相近的词语;
 * 
 *         <pre>
 * 
 *         Term word1 = new Term("content", "david"); //生成FuzzyQuery对象，初始化为null
 * 
 *         FuzzyQuery query = null;
 * 
 *         query = new FuzzyQuery(word1);
 * 
 *         //开始检索，并返回检索结果
 * 
 *         </pre>
 * 
 *         </li>
 * 
 *         <li>使用通配符搜索—WildcardQuery:Lucene也提供了通配符的查询，这就是WildcardQuery。
 *         通配符“?”代表1个字符，而“*”则代表0至多个字符。不过通配符检索和上面的FuzzyQuery由于需要对字段关键字进行字符串匹配，所以，
 *         在搜索的性能上面会受到一些影响。
 * 
 *         <pre>
 * 
 *         //构造词条
 * 
 *         Term word1 = new Term("content", "*ever");
 * 
 *         Term word2 = new Term("content", "wh?ever");
 * 
 *         Term word3 = new Term("content", "h??ever");
 * 
 *         Term word4 = new Term("content", "ever*");
 * 
 *         //生成WildcardQuery对象，初始化为null
 * 
 *         WildcardQuery query = null;
 * 
 *         //用于保存检索结果
 * 
 *         Hits hits = null;
 * 
 * 
 * 
 *         query = new WildcardQuery(word1);
 * 
 *         //开始第一次检索，并返回检索结果
 * 
 *         </pre>
 * 
 *         </li>
 *         </ul>
 * 
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchableField {

	/**
	 * 名称，默认与字段名一致
	 * 
	 * @return
	 */
	String value() default "";

	/**
	 * 是否存储此字段
	 * 
	 * @return
	 */
	Field.Store store() default Field.Store.YES;

	/**
	 * @return 删除HTML代码
	 * @author mawm at 2013-4-15 下午3:34:07
	 */
	public boolean parseHtml() default true;

	// 权重分
	public float boost() default 10;

	/**
	 * 是否将此字段加入到索引中（可用作检索条件）
	 * 
	 * @return
	 */
	Field.Index index() default Field.Index.NO;

	/**
	 * @return 标记这个字段是否是主键字段,用来在进行删除索引的操作的时候被引用
	 * @author mawm at 2013-4-15 下午3:34:07
	 */
	public boolean pk() default false;

	/**
	 * @return 当pk=true的时候,使用默认的设置值index =
	 *         Index.NOT_ANALYZED,如果需要自己自定义,那么这个值设置为false
	 * @author mawm at 2013-4-15 下午3:34:07
	 */
	public boolean pkUseDefault() default true;

}