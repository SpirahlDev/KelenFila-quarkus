// package org.spirahldev.kelenFila.common.helpers;



// import com.fasterxml.jackson.core.JsonProcessingException;
// import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
// import io.quarkus.hibernate.orm.panache.PanacheQuery;
// import io.quarkus.hibernate.orm.panache.PanacheRepository;
// import io.quarkus.panache.common.Page;
// import jakarta.transaction.Transactional;
// import jakarta.ws.rs.core.Response;
// import org.jose4j.json.internal.json_simple.JSONObject;
// import org.spirahldev.kelenFila.common.constants.AppStatusCode;

// import java.util.List;
// import java.util.Map;

// public abstract class CrudEndPointImpl<T extends PanacheEntityBase> implements CrudEndPoint<T> {

//     private PanacheRepository<T> t;

//     public PanacheRepository<T> getT() {
//         return t;
//     }

//     public void setT(PanacheRepository<T> t) {
//         this.t = t;
//         System.out.println(t.toString());
//     }

//     @Override
//     public Response create(Object entry) throws Exception {
//         throw new Exception("Not yet");
//     }

//     @Override
//     public Response get(Long id) throws JsonProcessingException {
//         AppResponse<T> response = new AppResponse<>();

//         T livingData = t.find("id", id).firstResult();

//         if (livingData == null) {
//             response.setStatusCode(AppStatusCode.RESOURCE_NOT_FOUND);
//             return Response.status(Response.Status.NOT_FOUND).entity(response).build();
//         }             

//         response.setStatusCode(AppStatusCode.SUCCESS_OPERATION);
//         response.setData(livingData);
//         return Response.status(200).entity(response).build();
//     }

//     @Override
//     @Transactional
//     public Response delete(Long id) throws Exception {
//         AppResponse<T> response = new AppResponse<>();

//         T livingData = t.findById(id);

//         if (livingData == null) {
//             response.setStatusCode(AppStatusCode.RESOURCE_NOT_FOUND);
//             return Response.status(Response.Status.NOT_FOUND).entity(response).build();
//         }

//         if (t.deleteById(id)) {
//             response.setStatusCode(AppStatusCode.SUCCESS_OPERATION);
//             return Response.status(Response.Status.NO_CONTENT).entity(response).build();
//         } else {
//             response.setStatusCode(AppStatusCode.INTERNAL_SERVER_ERROR);
//             return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
//         }

//     }



//     @Override
//     public Response paginate(int size, int page) {
//         System.out.println("-----------------------------");
//         System.out.println(t.toString());
//         System.out.println("-----------------------------");
//         AppResponse<Pagination<T>> response = new AppResponse<>();

//         try {

//             response.setStatusCode(AppStatusCode.SUCCESS_OPERATION);
//             Pagination<T> pagination = new Pagination<>();
//             int per_page = size > 0 ? size : 25;
//             int current_page = page > 0 ? page : 1;

//             System.out.println(t.count());
//             // create a query for all living persons
//             PanacheQuery<T> livingData = t.find("(deleted=:deleted OR deleted is NULL)", Map.of("deleted", false));
//             Long total = livingData.count();
//             // make it use pages of 25 entries at a time
//             livingData.page(Page.ofSize(per_page)).pageCount();

//             int total_page = livingData.page(Page.ofSize(per_page)).pageCount();
//             // get the first page
//             List<T> firstPage = livingData.page(current_page - 1, per_page).list();

//             //construct pagination model data
//             pagination.setTotal(total);
//             pagination.setTotal_of_pages(total_page);
//             pagination.setItems(firstPage);
//             pagination.setCurrent_page(current_page);
//             pagination.setNext_page(current_page + 1 <= total_page ? current_page + 1 : current_page);
//             response.setData(pagination);

//         } catch (Exception ex) {
//             ex.printStackTrace();
//             response.setStatusCode(AppStatusCode.VALIDATION_ERROR);
//         }

//         return Response.status(200).entity(response).build();
//     }

//     // @Override
//     // public Response paginateTrash(int size, int page) throws Exception {
//     //     System.out.println("-----------------------------");
//     //     System.out.println(t.toString());
//     //     System.out.println("-----------------------------");
//     //     AppResponse<Pagination<T>> response = new AppResponse<>();
//     //     response.setStatus_code(200);
//     //     response.setStatus_message("Success");
//     //     Pagination<T> pagination = new Pagination<>();
//     //     int per_page = size > 0 ? size : 25;
//     //     int current_page = page > 0 ? page : 1;

//     //     System.out.println(t.count());
//     //     // create a query for all living persons
//     //     PanacheQuery<T> livingData = t.find("deleted=:deleted", Map.of("deleted", true));
//     //     Long total = livingData.count();
//     //     // make it use pages of 25 entries at a time
//     //     livingData.page(Page.ofSize(per_page)).pageCount();

//     //     int total_page = livingData.page(Page.ofSize(per_page)).pageCount();
//     //     // get the first page
//     //     List<T> firstPage = livingData.page(current_page - 1, per_page).list();

//     //     //construct pagination model data
//     //     pagination.setTotal(total);
//     //     pagination.setTotal_of_pages(total_page);
//     //     pagination.setItems(firstPage);
//     //     pagination.setCurrent_page(current_page);
//     //     pagination.setNext_page(current_page + 1 <= total_page ? current_page + 1 : current_page);


//     //     response.setData(pagination);
//     //     return Response.status(200).entity(response).build();
//     // }

//     @Override
//     public Response stats() {
//         JSONObject vs = new JSONObject();
//         vs.put("total", t.count());
//         //vs.put("Entity", );
//         return Response.ok(vs.toJSONString()).build();
//     }
// }
