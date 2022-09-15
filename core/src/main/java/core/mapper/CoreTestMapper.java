package core.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import core.entity.CoreTest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoreTestMapper extends BaseMapper<CoreTest> {

    /**
     * sqlSession测试
     * @return
     */
    List<CoreTest> getById(@Param(value = "id") String id);

    /**
     * 动态sql， if标签测试
     */
    List<CoreTest> getAllByNameAndValue(@Param(value = "name") String name, @Param(value = "value") String value);

    /**
     *  方法不可以重载
     * @param coreTest
     * @return
     */
    List<CoreTest> getAllByEntity(@Param(value = "entity") CoreTest coreTest);

    /**
     * choose、when、otherwise标签 类似于switch-case
     */
    List<CoreTest> getChooseAll(@Param(value = "entity") CoreTest coreTest);

    Page<CoreTest> getAllPage(Page page, @Param(value = "entity") CoreTest coreTest);

    /**
     * trim、where、set
     *
     * where 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。而且，若子句的开头为 “AND” 或 “OR”，where 元素也会将它们去除。
     *
     * 如果 where 元素与你期望的不太一样，你也可以通过自定义 trim 元素来定制 where 元素的功能。比如，和 where 元素等价的自定义 trim 元素为：
     * <trim prefix="WHERE" prefixOverrides="AND |OR ">
     *   ...
     * </trim>
     *
     * 用于动态更新语句的类似解决方案叫做 set。set 元素可以用于动态包含需要更新的列，忽略其它不更新的列。比如
     * <update id="updateAuthorIfNecessary">
     *   update Author
     *     <set>
     *       <if test="username != null">username=#{username},</if>
     *       <if test="password != null">password=#{password},</if>
     *       <if test="email != null">email=#{email},</if>
     *       <if test="bio != null">bio=#{bio}</if>
     *     </set>
     *   where id=#{id}
     * </update>
     *
     * 这个例子中，set 元素会动态地在行首插入 SET 关键字，并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。
     * 或者，你可以通过使用trim元素来达到同样的效果：
     * <trim prefix="SET" suffixOverrides=",">
     *   ...
     * </trim>
     */

    /**
     * foreach
     * 动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）。比如：
     *
     * <select id="selectPostIn" resultType="domain.blog.Post">
     *   SELECT *
     *   FROM POST P
     *   <where>
     *     <foreach item="item" index="index" collection="list"
     *         open="ID in (" separator="," close=")" nullable="true">
     *           #{item}
     *     </foreach>
     *   </where>
     * </select>
     * foreach 元素的功能非常强大，它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。
     * 它也允许你指定开头与结尾的字符串以及集合项迭代之间的分隔符。这个元素也不会错误地添加多余的分隔符，看它多智能！
     *
     * 提示 你可以将任何可迭代对象（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 foreach。
     * 当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。当使用 Map 对象（或者 Map.Entry 对象的集合）时，
     * index 是键，item 是值。
     */


    /**
     * script
     * 要在带注解的映射器接口类中使用动态 SQL，可以使用 script 元素。比如:
     *
     *     @Update({"<script>",
     *       "update Author",
     *       "  <set>",
     *       "    <if test='username != null'>username=#{username},</if>",
     *       "    <if test='password != null'>password=#{password},</if>",
     *       "    <if test='email != null'>email=#{email},</if>",
     *       "    <if test='bio != null'>bio=#{bio}</if>",
     *       "  </set>",
     *       "where id=#{id}",
     *       "</script>"})
     *     void updateAuthorValues(Author author);
     */


    /**
     * bind
     * bind 元素允许你在 OGNL 表达式以外创建一个变量，并将其绑定到当前的上下文。比如：
     *
     * <select id="selectBlogsLike" resultType="Blog">
     *   <bind name="pattern" value="'%' + _parameter.getTitle() + '%'" />
     *   SELECT * FROM BLOG
     *   WHERE title LIKE #{pattern}
     * </select>
     */
}
