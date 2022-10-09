package com.generador.de.diamantes.fire.hcgd.Views.userMain.main.Components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.listaActividades.ui.DiamantesFreeFireTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppPoliticsScreen(

) {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        backgroundColor = Color.White
    ) {
        LazyColumn() {
            item {
                Column(Modifier.padding(8.dp)) {
                    Text(text = "Política de privacidad", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                    Text(
                        text = """ 
                        Andrade ha creado la aplicación Generador de Diamantes como una aplicación gratuita. Este SERVICIO es proporcionado por Andrade sin costo alguno y está destinado a ser utilizado como tal.
                        
                        Esta página se utiliza para informar a los visitantes con respecto a mis políticas con la recopilación, el uso y la divulgación de información personal si alguien decidió utilizar mi Servicio.
                        
                        Si decide utilizar mi Servicio, entonces acepta la recopilación y el uso de la información en relación con esta política. La información personal que recojo se utiliza para proporcionar y mejorar el Servicio. No utilizaré ni compartiré su información con nadie, excepto como se describe en esta Política de Privacidad.
                        
                        Los términos utilizados en esta Política de Privacidad tienen el mismo significado que en nuestros Términos y Condiciones, a los que se puede acceder en Generador de Diamantes, a menos que se defina lo contrario en esta Política de Privacidad
                    """.trimIndent(), fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Obtenimiento y uso de la información",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = """Para una mejor experiencia, al utilizar nuestro Servicio, puedo requerir que nos proporcione cierta información de identificación personal, incluyendo, pero no limitado a, el Almacenamiento. La información que solicite se conservará en su dispositivo y no será recogida por mí de ninguna manera.

La aplicación sí utiliza servicios de terceros que pueden recopilar información utilizada para identificarte.

Enlace a la política de privacidad de los proveedores de servicios de terceros utilizados por la app""",
                        fontSize = 20.sp
                    )
                    TextButton(onClick = {
                        uriHandler.openUri("https://policies.google.com/privacy")
                    }) {
                        Text(text = "https://policies.google.com/privacy")
                    }
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Datos de registro",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = """ 
                       Quiero informarle de que siempre que utiliza mi Servicio, en caso de error en la aplicación, recojo datos e información (a través de productos de terceros) en su teléfono, llamados Datos de Registro. Estos datos de registro pueden incluir información como la dirección del Protocolo de Internet ("IP") de su dispositivo, el nombre del dispositivo, la versión del sistema operativo, la configuración de la aplicación cuando utiliza mi Servicio, la hora y la fecha de su uso del Servicio y otras estadísticas.
                    """.trimIndent(), fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Cookies",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = """ 
                                Las cookies son archivos con una pequeña cantidad de datos que se utilizan habitualmente como identificadores únicos anónimos. Se envían a su navegador desde los sitios web que visita y se almacenan en la memoria interna de su dispositivo       
                                Este Servicio no utiliza estas "cookies" explícitamente. Sin embargo, la aplicación puede utilizar código y bibliotecas de terceros que utilizan "cookies" para recopilar información y mejorar sus servicios. Usted tiene la opción de aceptar o rechazar estas "cookies" y saber cuándo se envía una "cookie" a su dispositivo. Si decide rechazar nuestras cookies, es posible que no pueda utilizar algunas partes de este Servicio.
                                             
                         """.trimIndent(), fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Proovedores de servicios",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = """ 
                            Puedo emplear a terceras empresas y personas debido a las siguientes razones:

                            ● Para facilitar nuestro Servicio;

                            ● Para prestar el Servicio en nuestro nombre;

                            ● Para realizar servicios relacionados con el Servicio; o

                            ● Para ayudarnos a analizar cómo se utiliza nuestro Servicio.

                            Quiero informar a los usuarios de este Servicio que estos terceros tienen acceso a su Información Personal. El motivo es realizar las tareas que se les asignan en nuestro nombre. Sin embargo, están obligados a no divulgar ni utilizar la información para ningún otro fin.
                        """.trimIndent(), fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Seguridad",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = """ 
                            Valoro su confianza al proporcionarnos su información personal, por lo que nos esforzamos por utilizar medios comercialmente aceptables para protegerla. Pero recuerde que ningún método de transmisión por Internet, o método de almacenamiento electrónico es 100% seguro y fiable, y no puedo garantizar su absoluta seguridad.""".trimIndent(),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Enlaces a otros sitios",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text =
                        """ 
                        Este Servicio puede contener enlaces a otros sitios. Si hace clic en un enlace de terceros, será dirigido a ese sitio. Tenga en cuenta que estos sitios externos no son operados por mí. Por lo tanto, le aconsejo encarecidamente que revise la política de privacidad de estos sitios web. No tengo ningún control ni asumo ninguna responsabilidad por el contenido, las políticas de privacidad o las prácticas de los sitios o servicios de terceros.    """,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Privacidad de los niños",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text =
                        """Estos Servicios no se dirigen a personas menores de 13 años. No recojo a sabiendas información personal identificable de niños menores de 13 años. En el caso de que descubra que un niño menor de 13 años me ha proporcionado información personal, la borro inmediatamente de nuestros servidores. Si usted es un padre o tutor y tiene conocimiento de que su hijo nos ha proporcionado información personal, le ruego que se ponga en contacto conmigo para que pueda realizar las acciones necesarias. """,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Cambios en esta política de privacidad",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text =
                        """ Es posible que actualice nuestra política de privacidad de vez en cuando. Por lo tanto, le aconsejo que revise esta página periódicamente para ver si hay cambios. Le notificaré cualquier cambio publicando la nueva Política de Privacidad en esta página.

Esta política es efectiva a partir del 2022-05-29""".trimMargin(),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(
                        text = "Contacto",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text =
                        """Si tiene alguna pregunta o sugerencia sobre mi política de privacidad, no dude en ponerse en contacto conmigo en relangamerff11@gmail.com""".trimMargin(),
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))




                }


            }
        }


    }


}

@Preview
@Composable
fun Poolitics() {
    DiamantesFreeFireTheme() {
        AppPoliticsScreen()
    }
}