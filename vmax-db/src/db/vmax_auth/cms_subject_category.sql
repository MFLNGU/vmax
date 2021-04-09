drop table if exists cms_subject_category;

/*==============================================================*/
/* Table: cms_subject_category                                  */
/*==============================================================*/
create table cms_subject_category
(
   id                   bigint not null auto_increment,
   name                 varchar(100),
   icon                 varchar(500) comment '分类图标',
   subject_count        int comment '专题数量',
   show_status          int(2),
   sort                 int,
   primary key (id)
);

alter table cms_subject_category comment '专题分类表';
