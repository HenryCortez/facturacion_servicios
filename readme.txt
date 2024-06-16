/auth/login --- Todos-- post
{
    "username": "aa",
    "password": "aa"
}
/auth/register --- admin-- post
{
    "username": "aa",
    "password": "aa",
    "role": "admin" o "cajero"
}
/auth/{username} --- admin -- Delete y patch
{
    "password": "aa"   //solo patch
}
/auth   --- admin -- get
---------------------------------------------------
/api/client -- admin, cajero -- get
/api/client -- admin, cajero -- post -- opcional secondName y secondLastName
{
    "dni": "5151", 
    "firstName": "a",
    "lastName" : "a",
    "address" : "casa",
    "phone" : "21313",
    "email" : "a@a",
    "dniType" : "cedula/pasaporte"
}
/api/client/{id} -- admin, cajero -- Get
/api/client/{id} -- admin -- delete
/api/client/{id} -- admin -- put
{
    "firstName": "a",
    "lastName":"a",
    "secondName":"a",
    "secondLastName":"a",
    "address":"a",
    "phone":"123",
    "email":"a"
}
/api/client/dni/{dni} -- admin, cajero -- Get
/api/client/email/{email} -- admin, cajero -- Get
/api/client/phone/{phone} -- admin, cajero -- Get
---------------------------------------------------
/api/iva -- admin, cajero -- Get
---------------------------------------------------
/api/category -- admin, cajero -- Get
/api/category -- admin -- post
{
    "name": "a",
    "description":"a"
}
/api/category/{id} -- admin -- delete
/api/category/{id} -- admin -- put
{
    "name": "a",
    "description":"a"
}
/api/category/{id} -- admin, cajero -- Get
/api/category/name/{name} -- admin, cajero -- Get
---------------------------------------------------
/api/productos -- admin, cajero -- Get
/api/productos -- admin -- post
{
    "name": "a",
    "category": 1,
    "image": "image en 64bytes",
    "stock": 100,
    "price": 50.00
}
/api/productos/{id} -- admin, cajero -- get
/api/productos/{id} -- admin -- put / todos opcionales
{
    "name": "a",
    "category": 1,
    "image": "image en 64bytes",
    "stock": 100,
    "price": 50.00
}
/api/productos/activation/{id} -- admin -- put //activa o desactiva el producto forzozamente. No nesesita json
/api/productos/deactivate -- admin, cajero -- get //muestra productos fuera de servicios
/api/productos/iva -- admin -- put // cambia el iva de todos los productos existentes.
{
    "id": 1, 2 o 3
}
/api/productos/name/{name} -- admin, cajero -- get
---------------------------------------------------
/api/sales -- admin, cajero -- Get
/api/sales -- admin, cajero -- post
{
    "clientId": 0, para consumidor final
    "saleDetails": [
        {
            "productId": 4,
            "quantity": 5
        },
        {
            "productId": 2,
            "quantity": 5
        }
    ]
}
/api/sales/{id} -- admin, cajero -- Get
/api/sales/client/{clientId} -- admin, cajero -- Get
/api/sales/date-range -- admin, cajero -- Get
/api/sales/date/{date} -- admin, cajero -- Get
/api/sales/update-client -- admin, cajero -- put
{
    "saleId": 1,
    "newClientId": 4
}
