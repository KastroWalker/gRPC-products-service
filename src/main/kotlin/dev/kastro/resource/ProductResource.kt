package dev.kastro.resource

import dev.kastro.FindByIdServiceRequest
import dev.kastro.ProductServiceRequest
import dev.kastro.ProductServiceResponse
import dev.kastro.ProductsServiceGrpc
import dev.kastro.dto.ProductReq
import dev.kastro.exceptions.BaseBusinessException
import dev.kastro.service.ProductService
import dev.kastro.util.ValidationUtil
import io.grpc.stub.StreamObserver
import io.micronaut.grpc.annotation.GrpcService

@GrpcService
class ProductResource(private val productService: ProductService) : ProductsServiceGrpc.ProductsServiceImplBase() {
    override fun create(request: ProductServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {
            val payload = ValidationUtil.validatePayload(request)
            val productReq = ProductReq(
                name = payload.name, price = payload.price, quantityInStock = payload.quantityInStock
            )
            val productRes = productService.create(productReq)
            val productResponse = ProductServiceResponse.newBuilder()
                .setId(productRes.id)
                .setName(productRes.name)
                .setPrice(productRes.price)
                .setQuantityInStock(productRes.quantityInStock)
                .build()

            responseObserver?.onNext(productResponse)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                ex.statusCode()
                    .toStatus()
                    .withDescription(ex.errorMessage())
                    .asRuntimeException()
            )
        }
    }

    override fun findById(request: FindByIdServiceRequest?, responseObserver: StreamObserver<ProductServiceResponse>?) {
        try {

            val productRes = productService.findById(request!!.id)
            val productResponse = ProductServiceResponse.newBuilder()
                .setId(productRes.id)
                .setName(productRes.name)
                .setPrice(productRes.price)
                .setQuantityInStock(productRes.quantityInStock)
                .build()

            responseObserver?.onNext(productResponse)
            responseObserver?.onCompleted()
        } catch (ex: BaseBusinessException) {
            responseObserver?.onError(
                ex.statusCode()
                    .toStatus()
                    .withDescription(ex.errorMessage())
                    .asRuntimeException()
            )
        }
    }
}
