package com.src.appmobilecomputing.ui.orarilezioni

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.src.appmobilecomputing.adapter.CustomExpandableListAdapter
import com.src.appmobilecomputing.databinding.FragmentOrarilezioniBinding
import org.json.JSONObject
import java.net.URL


class OrariLezioniFragment : Fragment() {

    private var _binding: FragmentOrarilezioniBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val orariGiorniSottocategorieMap = HashMap<String,String>();
    private var popupWindow: PopupWindow? = null;
    private var dati_orarilezioni: String = "";
    private var sharedPreferences: SharedPreferences? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val orarilezioniViewModel = ViewModelProvider(this).get(OrariLezioniViewModel::class.java);

        _binding = FragmentOrarilezioniBinding.inflate(inflater, container, false);
        val root: View = binding.root;

        val button:Button = binding.buttonLoadDatiOrarilezioni;
        binding.buttonLoadDatiOrarilezioni.setOnClickListener {
            Toast.makeText(context, "avvio sincronizzazione", Toast.LENGTH_SHORT).show();
            syncronizeData();
        }

        val textView: TextView = binding.textOrarilezioni;
        orarilezioniViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it;
            textView.setVisibility(View.INVISIBLE);
        }

        //Inizio
        sharedPreferences = this.requireActivity().getSharedPreferences("my_sharedPreferences",MODE_PRIVATE);
        //println("Inizio lettura orari");
        //println("dati_orarilezioni = "+Resources.getSystem().getString(com.src.appmobilecomputing.R.string.dati_orarilezioni));
        //val sharedPreferences: SharedPreferences = this.requireActivity().getSharedPreferences("my_sharedPreferences",MODE_PRIVATE);
        sharedPreferences!!.edit().clear().commit();
        dati_orarilezioni = sharedPreferences!!.getString("dati_orarilezioni","").toString();
        //Il dato non è stato salvato nel database locale quindi imposto un dato standard
        if (dati_orarilezioni?.length!!<=0) {
            Toast.makeText(context, "dati_orarilezioni vuoto", Toast.LENGTH_SHORT).show();
            dati_orarilezioni = "{\"categorie\":[{\"id\":\"0\",\"nome\":\"Corsi di Laurea Triennale\\/Magistrale Ciclo Unico\",\"sottocategorie\":[{\"id\":\"2\",\"descrizione\":\"Il Corso intende fornire agli studenti una formazione moderna e competitiva in Management e Accounting. I laureati in Economia Aziendale saranno in grado di comprendere il funzionamento delle imprese e dei sistemi economici odierni, avendo la giusta preparazione per affrontare le principali sfide delle diverse aree aziendali.\",\"nome\":\"Economia Aziendale\",\"giorni\":[{\"giorno\":\"Luned\\u00ec\",\"orari\":[{\"orario\":\"09:00-11:00\",\"contenuto\":\"Materia 1\"},{\"orario\":\"11:00-13:00\",\"contenuto\":\"Materia 2\"},{\"orario\":\"14:00-16:00\",\"contenuto\":\"Materia 3\"},{\"orario\":\"16:00-18:00\",\"contenuto\":\"Materia 4\"}]},{\"giorno\":\"Marted\\u00ec\",\"orari\":[{\"orario\":\"09:00-11:00\",\"contenuto\":\"Materia 5\"},{\"orario\":\"11:00-13:00\",\"contenuto\":\"Materia 6\"},{\"orario\":\"14:00-16:00\",\"contenuto\":\"Materia 7\"},{\"orario\":\"16:00-18:00\",\"contenuto\":\"Materia 8\"}]}]},{\"id\":\"3\",\"descrizione\":\"Il Corso di Laurea in Ingegneria e Scienze Informatiche per la Cybersecurity nasce col preciso intento di fornire una risposta concreta allo \\u201cskills shortage\\u201d nel campo della sicurezza informatica. Il corso \\u00e8 pertanto pensato come percorso triennale al fine di formare nel pi\\u00f9 breve tempo possibile personale tecnico specializzato immediatamente impiegabile nel settore della cybersecurity.\",\"nome\":\"Ingegneria Informatica Cybersecurity Sede di Nola\",\"giorni\":[{\"giorno\":\"Luned\\u00ec\",\"orari\":[{\"orario\":\"10:00-13:00\",\"contenuto\":\"Fisica Generale\"},{\"orario\":\"14:00-17:00\",\"contenuto\":\"Programm. Dispositivi Mobili\"}]},{\"giorno\":\"Marted\\u00ec\",\"orari\":[{\"orario\":\"10:00-13:00\",\"contenuto\":\"Architettura Elaboratori\"},{\"orario\":\"14:00-17:00\",\"contenuto\":\"Aspetti Org. Gest. Cybersecurity\"}]},{\"giorno\":\"Mercoled\\u00ec\",\"orari\":[{\"orario\":\"10:00-13:00\",\"contenuto\":\"Aspetti Org. Gest. Cybersecurity\"}]},{\"giorno\":\"Gioved\\u00ec\",\"orari\":[{\"orario\":\"10:00-13:00\",\"contenuto\":\"Architettura Elaboratori\"}]},{\"giorno\":\"Venerd\\u00ec\",\"orari\":[{\"orario\":\"10:00-13:00\",\"contenuto\":\"Fisica Generale\"},{\"orario\":\"14:00-17:00\",\"contenuto\":\"Programm. Dispositivi Mobili\"}]}]}]},{\"id\":\"1\",\"nome\":\"Corsi di Laurea Magistrale\",\"sottocategorie\":[{\"id\":\"4\",\"descrizione\":\"L\\u2019elevata specializzazione del Corso, nell\\u2019ambito dell\\u2019amministrazione, finanza e controllo d\\u2019azienda, consente di acquisire una formazione trasversale e dal respiro internazionale. L\\u2019alta qualificazione nel settore della consulenza professionale garantisce una forte competitivit\\u00e0 spendibile nel mercato del lavoro.\",\"nome\":\"Amministrazione Finanza Consulenza Aziendale\",\"giorni\":[{\"giorno\":\"Luned\\u00ec\",\"orari\":[{\"orario\":\"09:00-11:00\",\"contenuto\":\"Materia 1\"},{\"orario\":\"11:00-13:00\",\"contenuto\":\"Materia 2\"},{\"orario\":\"14:00-16:00\",\"contenuto\":\"Materia 3\"},{\"orario\":\"16:00-18:00\",\"contenuto\":\"Materia 4\"}]},{\"giorno\":\"Marted\\u00ec\",\"orari\":[{\"orario\":\"09:00-11:00\",\"contenuto\":\"Materia 5\"},{\"orario\":\"11:00-13:00\",\"contenuto\":\"Materia 6\"},{\"orario\":\"14:00-16:00\",\"contenuto\":\"Materia 7\"},{\"orario\":\"16:00-18:00\",\"contenuto\":\"Materia 8\"}]}]}]}]}";
            dati_orarilezioni=dati_orarilezioni.replace("\n","");
            setDataInSharedPreferences("dati_orarilezioni",dati_orarilezioni);
        }
        //Il dato è già salvato nel database locale
        else {
            //Toast.makeText(context, "dati_orarilezioni = "+dati_orarilezioni, Toast.LENGTH_SHORT).show();
        }

        //val apiResponseJson = dati_orarilezioni; //URL("https://uniparthenope.piattaformasicura.com/test1.php").readText();
        //val apiResponseJson = "{\"categorie\":[{\"id\":\"0\",\"nome\":\"Corsi di Laurea Triennale\\/Magistrale Ciclo Unico\",\"sottocategorie\":[{\"id\":\"2\",\"descrizione\":\"Il Corso intende fornire agli studenti una formazione moderna e competitiva in Management e Accounting. I laureati in Economia Aziendale saranno in grado di comprendere il funzionamento delle imprese e dei sistemi economici odierni, avendo la giusta preparazione per affrontare le principali sfide delle diverse aree aziendali.\",\"nome\":\"Economia Aziendale\",\"giorni\":[{\"giorno\":\"lunedi\",\"orari\":[{\"orario\":\"09:00-11:00\",\"contenuto\":\"Materia 1\"},{\"orario\":\"11:00-13:00\",\"contenuto\":\"Materia 2\"},{\"orario\":\"14:00-16:00\",\"contenuto\":\"Materia 3\"},{\"orario\":\"16:00-18:00\",\"contenuto\":\"Materia 4\"}]},{\"giorno\":\"martedi\",\"orari\":[{\"orario\":\"09:00-11:00\",\"contenuto\":\"Materia 5\"},{\"orario\":\"11:00-13:00\",\"contenuto\":\"Materia 6\"},{\"orario\":\"14:00-16:00\",\"contenuto\":\"Materia 7\"},{\"orario\":\"16:00-18:00\",\"contenuto\":\"Materia 8\"}]}]},{\"id\":\"3\",\"descrizione\":\"Il Corso di Laurea in Ingegneria e Scienze Informatiche per la Cybersecurity nasce col preciso intento di fornire una risposta concreta allo \\u201cskills shortage\\u201d nel campo della sicurezza informatica. Il corso \\u00e8 pertanto pensato come percorso triennale al fine di formare nel pi\\u00f9 breve tempo possibile personale tecnico specializzato immediatamente impiegabile nel settore della cybersecurity.\",\"nome\":\"Ingegneria e Scienze Informatiche per la Cybersecurity \\u2013 Sede di Nola\"}]},{\"id\":\"1\",\"nome\":\"Corsi di Laurea Magistrale\",\"sottocategorie\":[{\"id\":\"4\",\"descrizione\":\"L\\u2019elevata specializzazione del Corso, nell\\u2019ambito dell\\u2019amministrazione, finanza e controllo d\\u2019azienda, consente di acquisire una formazione trasversale e dal respiro internazionale. L\\u2019alta qualificazione nel settore della consulenza professionale garantisce una forte competitivit\\u00e0 spendibile nel mercato del lavoro.\",\"nome\":\"Amministrazione, Finanza e Consulenza Aziendale\"}]}]}";
        //val apiResponse=apiResponseJson.replace("\n","");
        //println(apiResponse);
        //println("Fine lettura orari");
        reloadDataOnList();
        //Fine

        /*binding.buttonLoadOrariLezioni.setOnClickListener { view ->
            Toast.makeText(context, apiResponse, Toast.LENGTH_SHORT).show();
        }*/

        return root;
    }
    private fun setDataInSharedPreferences(name:String,data:String) {
        var editorPreferences = sharedPreferences!!.edit();
        editorPreferences.putString(name,data);
        editorPreferences.commit();
    }
    private fun syncronizeData() {
        //binding.textHome.text = "Caricamento in corso....attendere";
        binding.buttonLoadDatiOrarilezioni.text = "Attendere...";
        dati_orarilezioni = URL("https://uniparthenope.piattaformasicura.com/test1.php").readText();
        dati_orarilezioni=dati_orarilezioni.replace("\n","");
        setDataInSharedPreferences("dati_orarilezioni",dati_orarilezioni);
        reloadDataOnList();
        binding.buttonLoadDatiOrarilezioni.text = "Sincronizza dati";
    }
    private fun reloadDataOnList() {
        dati_orarilezioni = sharedPreferences!!.getString("dati_orarilezioni","").toString();
        val category = ArrayList<String>();
        //private var expandableListView: ExpandableListView? = null
        var adapterList: ExpandableListAdapter? = null
        var titleList: List<String>? = null
        val expandableListView: ExpandableListView = binding.expendableList;
        //println("expandableListView-->"+expandableListView);
        if (expandableListView != null) {
            //com.src.appmobilecomputing.data.ExpandableListData.data
            //val listData = data

            val listData = HashMap<String, List<String>>();
            val obj = JSONObject(dati_orarilezioni);
            val categorieArray = obj.getJSONArray("categorie");
            for (i in 0 until categorieArray.length()) {
                val categorieObj = categorieArray.getJSONObject(i);
                //println(categorieObj.getString("nome"));
                //println(dataobj);
                val mutableListTemp: MutableList<String> = java.util.ArrayList();
                val sottocategorieArray = categorieObj.getJSONArray("sottocategorie");
                for (j in 0 until sottocategorieArray.length()) {
                    val sottocategorieObj = sottocategorieArray.getJSONObject(j);
                    println(" - "+sottocategorieObj.getString("nome"));
                    mutableListTemp.add(sottocategorieObj.getString("nome"));
                    if (sottocategorieObj.has("giorni")) {
                        val giorniSottocategorieArray = sottocategorieObj.getJSONArray("giorni");
                        //println("giorni " + sottocategorieObj.getJSONArray("giorni"));
                        var orarioTemp = "";
                        for (k in 0 until giorniSottocategorieArray.length()) {
                            val giorniSottocategorieObj = giorniSottocategorieArray.getJSONObject(k);

                            println("- - " + giorniSottocategorieObj.getString("giorno"));
                            orarioTemp +=
                                    //"- - " +
                                giorniSottocategorieObj.getString("giorno")+"\n";
                            if (giorniSottocategorieObj.has("orari")) {
                                val orariGiorniSottocategorieArray = giorniSottocategorieObj.getJSONArray("orari");
                                for (x in 0 until orariGiorniSottocategorieArray.length()) {
                                    val orariGiorniSottocategorieObj = orariGiorniSottocategorieArray.getJSONObject(x);
                                    println(
                                        //"- - - "+
                                        "" + orariGiorniSottocategorieObj.getString("orario")+"\n"+
                                                "" + orariGiorniSottocategorieObj.getString("contenuto")
                                    );
                                    orarioTemp +=
                                            //"- - - "+
                                        "" + orariGiorniSottocategorieObj.getString("orario")+"\n"+
                                                "" + orariGiorniSottocategorieObj.getString("contenuto")+"\n\n";
                                }
                            }
                            orariGiorniSottocategorieMap.put(
                                sottocategorieObj.getString("nome"),
                                orarioTemp
                            );

                        }
                    }
                }
                listData[categorieObj.getString("nome")] = mutableListTemp;
            }
            titleList = ArrayList(listData!!.keys);
            adapterList = CustomExpandableListAdapter(this.requireActivity(), titleList as ArrayList<String>, listData);
            expandableListView!!.setAdapter(adapterList);

            /*val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LayoutParams.WRAP_CONTENT, // Width of popup window
                LayoutParams.WRAP_CONTENT // Window height
            );
            popupWindow.elevation = 100.0F;*/

            //val popupView: View = LayoutInflater.from(activity).inflate(R.layout.popup_layout, null)

            val dialogBuilder = AlertDialog.Builder(context);

            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                var testoTemp = orariGiorniSottocategorieMap.get( listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition) );
                /*Toast.makeText(
                    context,
                            //"Clicked:\n" +
                            //"groupPosition="+groupPosition+"\n"+
                            //"childPosition="+childPosition+"\n"+
                            (titleList as ArrayList<String>)[groupPosition] + "\n" +
                            listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition)+ "\n"+
                            orariGiorniSottocategorieMap.get( listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition) ),
                    Toast.LENGTH_SHORT
                ).show()*/
                dialogBuilder.setMessage(testoTemp)
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("CHIUDI", DialogInterface.OnClickListener {
                            dialog, id -> dialog.cancel()
                    })
                // negative button text and action
                /*.setNegativeButton("Cancel", DialogInterface.OnClickListener {
                        dialog, id -> dialog.cancel()
                })*/
                // create dialog box
                val alert = dialogBuilder.create();
                // set title for alert dialog box
                alert.setTitle(listData[(titleList as ArrayList<String>)[groupPosition]]!!.get(childPosition));
                // show alert dialog
                alert.show();
                println(testoTemp);
                false
            }
            /*expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(
                    context,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
            /*expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    context,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }*/
            /*expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    context,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }*/
        }
    }
}