drop table if exists pms_product_category;
create table pms_product_category
(
   id                   bigint not null,
   parent_id            bigint comment '上级分类的编号：0表示一级分类',
   name                 varchar(64) comment '名称',
   level                int(1) comment '分类级别：0->1级；1->2级',
   product_count        int comment '商品数量',
   product_unit         varchar(64) comment '商品单位',
   nav_status           int(1) comment '是否显示在导航栏：0->不显示；1->显示',
   show_status          int(1) comment '显示状态：0->不显示；1->显示',
   sort                 int comment '排序',
   icon                 varchar(255) comment '图标',
   keywords             varchar(255) comment '关键字',
   description          text comment '描述',
   created_time          datetime comment '创建时间',
   created_by            bigint comment '创建人',
   updated_time          datetime comment '更新时间',
   updated_by         bigint comment '更新人',
   primary key (id)
);