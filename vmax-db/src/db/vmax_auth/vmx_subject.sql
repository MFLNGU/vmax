drop table if exists cms_subject;

/*==============================================================*/
/* Table: cms_subject                                           */
/*==============================================================*/
create table cms_subject
(
   id                   bigint not null auto_increment,
   category_id          bigint,
   title                varchar(100),
   pic                  varchar(500) comment '专题主图',
   product_count        int comment '关联产品数量',
   recommend_status     int(1),
   create_time          datetime,
   collect_count        int,
   read_count           int,
   comment_count        int,
   album_pics           varchar(1000) comment '画册图片用逗号分割',
   description          varchar(1000),
   show_status          int(1) comment '显示状态：0->不显示；1->显示',
   content              text,
   forward_count        int comment '转发数',
   category_name        varchar(200) comment '专题分类名称',
   primary key (id)
);

alter table cms_subject comment '专题表';

alter table cms_subject add constraint FK_Reference_28 foreign key (category_id)
      references cms_subject_category (id) on delete restrict on update restrict;
