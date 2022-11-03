package com.fadedbytes.examenunopau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fadedbytes.examenunopau.ui.theme.ExamenUnoPauTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    lateinit var loginUser: MutableState<String>;
    lateinit var loginPass: MutableState<String>;

    lateinit var coroutineScope: CoroutineScope
    lateinit var scaffoldState: ScaffoldState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreeen(Screen.LOGIN)

    }

    enum class Screen {
        LOGIN,
        REGISTER,
        HOME,
        LOVED
    }

    private fun setScreeen(screen: Screen) {
        setContent {
            ExamenUnoPauTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    scaffoldState = rememberScaffoldState()
                    coroutineScope = rememberCoroutineScope()

                    Scaffold(
                        topBar = {
                            LoginTopbar()
                        },
                        scaffoldState = scaffoldState
                    ) {

                        Column(

                        ) {
                            when (screen) {
                                Screen.LOGIN -> LoginScreen()
                                Screen.REGISTER -> RegisterScreen()
                                Screen.HOME -> HomeScreen()
                                Screen.LOVED -> HomeScreen(true)
                            }

                            NavButtons()
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun LoginScreen() {
        LoginBox()
    }
    @Composable
    fun LoginTopbar() {
        TopAppBar(
            title = { Text(text = "Money Films")},
            elevation = 4.dp,
            navigationIcon = {
                IconButton(onClick = {  }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "More"
                    )
                }
            }
        )
    }

    @Composable
    fun NavButtons() {
        BottomNavigation(
            // Fix to bottom of the screen
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp
        ) {
            BottomNavigationItem(selected = false, onClick = { setScreeen(Screen.HOME) }, icon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            })
            BottomNavigationItem(selected = false, onClick = { setScreeen(Screen.LOVED) }, icon = {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Favoritas")
            })
            BottomNavigationItem(selected = false, onClick = {  }, icon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "Profile")
            })
        }
    }

    @Composable
    fun LoginBox() {
        loginUser = remember {
            mutableStateOf("")
        }
        loginPass = remember {
            mutableStateOf("")
        }
        
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                OutlinedTextField(
                    value = loginUser.value,
                    onValueChange = { loginUser.value = it },
                    label = { Text(text = "Usuario / Email") },
                )

                OutlinedTextField(
                    value = loginPass.value,
                    onValueChange = { loginPass.value = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                )

                Button(onClick = {
                    if (loginUser.value == "admin" && loginPass.value == "admin") {
                        setScreeen(Screen.HOME)
                    } else {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("El login ha fallado :(")
                        }
                    }
                }) {
                    Text(text = "Entrar")
                }

                // clickable text
                Text(
                    text = "¿Aún no te has registrado?",
                    modifier = Modifier.clickable(onClick = { setScreeen(Screen.REGISTER) })
                )
            }
        }
    }

    @Preview
    @Composable
    fun RegisterScreen() {
        var inputUser by remember { mutableStateOf("") }
        var inputEmail by remember { mutableStateOf("") }
        var inputPass by remember { mutableStateOf("") }
        var inputPass2 by remember { mutableStateOf("") }

        // 6 false array
        val checkboxes = remember { mutableStateListOf(false, false, false, false, false, false) }

        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = inputUser,
                    onValueChange = { inputUser = it },
                    label = { Text(text = "Usuario") }
                )
                OutlinedTextField(
                    value = inputEmail,
                    onValueChange = { inputEmail = it },
                    label = { Text(text = "Email") }
                )
                OutlinedTextField(
                    value = inputPass,
                    onValueChange = { inputPass = it },
                    label = { Text(text = "Password") },
                    visualTransformation = PasswordVisualTransformation()
                )
                OutlinedTextField(
                    value = inputPass2,
                    onValueChange = { inputPass2 = it },
                    label = { Text(text = "Repite Password") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkboxes[5],
                                onCheckedChange = { checkboxes[5] = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Documentales")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkboxes[0],
                                onCheckedChange = { checkboxes[0] = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Deportes")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkboxes[1],
                                onCheckedChange = { checkboxes[1] = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Romance")
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(1f)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkboxes[2],
                                onCheckedChange = { checkboxes[2] = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Accion")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkboxes[3],
                                onCheckedChange = { checkboxes[3] = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Históricas")
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkboxes[4],
                                onCheckedChange = { checkboxes[4] = it },
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Sci-fi")
                        }
                    }
                }

                Button(onClick = {
                    if (
                        !inputUser.isBlank()
                        && !inputEmail.isBlank()
                        && !inputPass.isBlank()
                        && !inputPass2.isBlank()
                    ) {
                        if (inputPass == inputPass2) {
                            setScreeen(Screen.HOME)
                        } else {
                            coroutineScope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Las contraseñas no coinciden")
                            }
                        }
                    } else {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Rellena todos los campos")
                        }
                    }
                }) {
                    Text(text = "Registrarse")
                }
            }
        }
    }

    @Composable
    fun HomeScreen(onlyLoved: Boolean = false) {

        var filmExpanded = remember { mutableStateOf(-1) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            if (onlyLoved) {
                LovedMovies(filmExpanded)
            } else {
                AllMovies(filmExpanded)
            }
        }
    }

    @Composable
    fun Movie(
        title: String,
        score: Int,
        description: String,
        id: Int,
        expanded: Boolean = false,
        image: Int = R.drawable.default_movie,
        fullImage: Int = R.drawable.banner,
        onExpandPress: (Int) -> Unit = {},
    ) {
        Column(
            modifier = Modifier
                .background(color = Color(156, 156, 156))
                .padding(horizontal = 0.dp, vertical = 4.dp)
        ) {

            if (expanded) {
                Image(
                    painter = painterResource(id = fullImage),
                    contentDescription = "Movie Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = "Movie image",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(text = title, fontSize = 20.sp)
                    Row {
                        if (!expanded) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star icon",
                                tint = Color(255, 200, 0),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(text = "$score", fontSize = 16.sp)
                        } else {
                            Text(text = description, fontSize = 16.sp)
                        }
                    }
                }
            }

            IconButton(onClick = { onExpandPress(id) }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
            }
        }
    }

    @Composable
    fun AllMovies(filmExpanded: MutableState<Int>) {
        Movie(
            title = "Black Adam",
            score = 6,
            id = 1,
            description = "Dwayne Johnson interpreta a Black Adam, un supervillano que se enfrentará a Shazam, interpretado por Zachary Levi.",
            expanded = filmExpanded.value == 1,
        ) {
            filmExpanded.value = it
        }
        Movie(
            title = "Otra película",
            score = 4,
            id = 2,
            description = "Esta es la peli más aburrida que has visto jamás",
            expanded = filmExpanded.value == 2
        ) {
            filmExpanded.value = it;
        }
    }

    @Composable
    fun LovedMovies(filmExpanded: MutableState<Int>) {
        Movie(
            title = "Black Adam",
            score = 6,
            id = 1,
            description = "Dwayne Johnson interpreta a Black Adam, un supervillano que se enfrentará a Shazam, interpretado por Zachary Levi.",
            expanded = filmExpanded.value == 1,
        ) {
            filmExpanded.value = it
        }
    }
}

